package gov.hhs.fha.nhinc.docretrieve.entity;

import javax.xml.ws.WebServiceContext;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.common.nhinccommonproxy.RespondingGatewayCrossGatewayRetrieveSecuredRequestType;
import gov.hhs.fha.nhinc.docretrieve.DocRetrieveAuditLog;
import gov.hhs.fha.nhinc.gateway.aggregator.GetAggResultsDocRetrieveRequestType;
import gov.hhs.fha.nhinc.gateway.aggregator.GetAggResultsDocRetrieveResponseType;
import gov.hhs.fha.nhinc.gateway.aggregator.StartTransactionDocRetrieveRequestType;
import gov.hhs.fha.nhinc.gateway.aggregator.document.DocRetrieveAggregator;
import gov.hhs.fha.nhinc.gateway.aggregator.document.DocumentConstants;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenExtractor;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType.DocumentRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;

/**
 *
 *
 * @author Neil Webb
 */
public class EntityDocRetrieveSecuredImpl
{
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(EntityDocRetrieveSecuredImpl.class);
    private static final long SLEEP_MS = 1000;
    private static final long AGGREGATOR_TIMEOUT_MS = 40000;

    public RetrieveDocumentSetResponseType respondingGatewayCrossGatewayRetrieve(RetrieveDocumentSetRequestType body, WebServiceContext context)
    {
        log.debug("Begin EntityDocRetrieveSecuredImpl.respondingGatewayCrossGatewayRetrieve");
        RetrieveDocumentSetResponseType response = null;
        DocRetrieveAuditLog auditLog = new DocRetrieveAuditLog();
        DocRetrieveAggregator aggregator = new DocRetrieveAggregator();

        AssertionType assertion = SamlTokenExtractor.GetAssertion(context);

        auditLog.auditDocRetrieveRequest(body, assertion);

        try
        {
            String transactionId = startTransaction(aggregator, body);

            sendRetrieveMessages(transactionId, body, context);

            response = retrieveDocRetrieveResults(aggregator, transactionId);
        }
        catch(Throwable t)
        {
            log.error("Error occured processing doc retrieve on entity interface: " + t.getMessage(), t);
            response = createErrorResponse("Fault encountered processing internal document retrieve");
        }

        // Audit log - response
        auditLog.auditResponse(response, assertion);

        log.debug("End EntityDocRetrieveSecuredImpl.respondingGatewayCrossGatewayRetrieve");
        return response;
    }

    private void sendRetrieveMessages(String transactionId, RetrieveDocumentSetRequestType body, WebServiceContext context)
    {
        log.debug("Begin sendRetrieveMessages");
        for(DocumentRequest docRequest : body.getDocumentRequest())
        {
            RespondingGatewayCrossGatewayRetrieveSecuredRequestType nhinDocRetrieveMsg = new RespondingGatewayCrossGatewayRetrieveSecuredRequestType();

            // Set document request
            RetrieveDocumentSetRequestType nhinDocRequest = new RetrieveDocumentSetRequestType();
            nhinDocRetrieveMsg.setRetrieveDocumentSetRequest(nhinDocRequest);
            nhinDocRequest.getDocumentRequest().add(docRequest);
            nhinDocRetrieveMsg.setNhinTargetSystem(buildHomeCommunity(docRequest.getHomeCommunityId()));

            // Create and start doc retrieve sender thread
            DocRetrieveSender docRetrieveSender = new DocRetrieveSender(transactionId, nhinDocRetrieveMsg, context);
            docRetrieveSender.start();
        }
        log.debug("End sendRetrieveMessages");
    }

    private RetrieveDocumentSetResponseType retrieveDocRetrieveResults(DocRetrieveAggregator aggregator, String transactionId)
    {
        log.debug("Begin retrieveDocRetrieveResults");
        RetrieveDocumentSetResponseType response = null;
        boolean retrieveTimedOut = false;

        // Agggregator retrieve request message
        GetAggResultsDocRetrieveRequestType aggResultsRequest = new GetAggResultsDocRetrieveRequestType();
        aggResultsRequest.setTransactionId(transactionId);
        aggResultsRequest.setTimedOut(retrieveTimedOut);

        // Loop until responses are received
        long startTime = System.currentTimeMillis();
        while(response == null)
        {
            GetAggResultsDocRetrieveResponseType aggResultsResponse = aggregator.getAggResults(aggResultsRequest);
            String retrieveStatus = aggResultsResponse.getStatus();
            if(DocumentConstants.COMPLETE_TEXT.equals(retrieveStatus))
            {
                response = aggResultsResponse.getRetrieveDocumentSetResponse();
            }
            else if(DocumentConstants.FAIL_TEXT.equals(retrieveStatus))
            {
                log.error("Document retrieve aggregator reports failurt - returning error");
                response = createErrorResponse("Processing internal document retrieve");
            }
            else
            {
                retrieveTimedOut = retrieveTimedOut(startTime);
                if(retrieveTimedOut)
                {
                    aggResultsRequest.setTimedOut(retrieveTimedOut);
                    aggResultsResponse = aggregator.getAggResults(aggResultsRequest);
                    if(DocumentConstants.COMPLETE_TEXT.equals(retrieveStatus))
                    {
                        response = aggResultsResponse.getRetrieveDocumentSetResponse();
                    }
                    else
                    {
                        log.warn("Document retrieve aggregation timeout - returning error.");
                        response = createErrorResponse("Processing internal document retrieve - failure in timeout");
                    }
                }
                else
                {
                    try
                    {
                        Thread.sleep(SLEEP_MS);
                    }
                    catch(Throwable t){}
                }
            }
        }
        log.debug("End retrieveDocRetrieveResults");
        return response;
    }

    private boolean retrieveTimedOut(long startTime)
    {
        long timeout = startTime + AGGREGATOR_TIMEOUT_MS;
        return (timeout < System.currentTimeMillis());
    }

    private String startTransaction(DocRetrieveAggregator aggregator, RetrieveDocumentSetRequestType body)
    {
        StartTransactionDocRetrieveRequestType docRetrieveStartTransaction = new StartTransactionDocRetrieveRequestType();
        docRetrieveStartTransaction.setRetrieveDocumentSetRequest(body);
        log.debug("Starting doc retrieve transaction");
        String transactionId = aggregator.startTransaction(docRetrieveStartTransaction);
        log.debug("Doc retrieve transaction id: " + transactionId);
        return transactionId;
    }

    private NhinTargetSystemType buildHomeCommunity(String homeCommunityId)
    {
        NhinTargetSystemType nhinTargetSystem = new NhinTargetSystemType();
        HomeCommunityType homeCommunity = new HomeCommunityType();
        homeCommunity.setHomeCommunityId("urn:oid:" + homeCommunityId);
        nhinTargetSystem.setHomeCommunity(homeCommunity);
        return nhinTargetSystem;
    }
    
    private RetrieveDocumentSetResponseType createErrorResponse(String codeContext)
    {
        RetrieveDocumentSetResponseType response = new RetrieveDocumentSetResponseType();
        RegistryResponseType responseType = new RegistryResponseType();
        response.setRegistryResponse(responseType);
        responseType.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");
        RegistryErrorList regErrList = new RegistryErrorList();
        responseType.setRegistryErrorList(regErrList);
        RegistryError regErr = new RegistryError();
        regErrList.getRegistryError().add(regErr);
        regErr.setCodeContext(codeContext);
        regErr.setErrorCode("XDSRepositoryError");
        regErr.setSeverity("Error");
        return response;
    }

}
