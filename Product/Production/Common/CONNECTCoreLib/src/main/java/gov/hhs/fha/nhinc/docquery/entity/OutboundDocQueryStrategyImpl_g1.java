package gov.hhs.fha.nhinc.docquery.entity;

import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.docquery.nhin.proxy.NhinDocQueryProxy;
import gov.hhs.fha.nhinc.docquery.nhin.proxy.NhinDocQueryProxyObjectFactory;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.orchestration.OutboundResponseProcessor;
import gov.hhs.fha.nhinc.gateway.executorservice.ExecutorServiceHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implements the DocQuery client strategy for spec g1 endpoint
 *
 * @author paul.eftis
 */
public class OutboundDocQueryStrategyImpl_g1 extends OutboundDocQueryStrategy{

    private static Log log = LogFactory.getLog(OutboundDocQueryStrategyImpl_g1.class);
    
    public OutboundDocQueryStrategyImpl_g1(){
        
    }

    private Log getLogger(){
        return log;
    }


    /**
     * @param message contains request message to execute
     */
    @Override
    public void execute(OutboundDocQueryOrchestratable message){
        getLogger().debug("NhinDocQueryStrategyImpl_g1::execute");
        if(message instanceof OutboundDocQueryOrchestratable_a1){
            executeStrategy((OutboundDocQueryOrchestratable_a1)message);
        }else{
            // shouldn't get here
            getLogger().debug("NhinDocQueryStrategyImpl_g1 EntityDocQueryOrchestratable was not an EntityDocQueryOrchestratable_a1!!!");
            // throw new Exception("OutboundDocQueryStrategyImpl_g1 OutboundDocQueryOrchestratable was not an OutboundDocQueryOrchestratable_a1!!!");
        }
    }


    @SuppressWarnings("static-access")
    public void executeStrategy(OutboundDocQueryOrchestratable_a1 message){
        getLogger().debug("NhinDocQueryStrategyImpl_g1::executeStrategy");

        auditRequestMessage(message.getRequest(), NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION,
                NhincConstants.AUDIT_LOG_NHIN_INTERFACE, message.getAssertion(),
                message.getTarget().getHomeCommunity().getHomeCommunityId());
        try{
            NhinDocQueryProxy proxy = new NhinDocQueryProxyObjectFactory().getNhinDocQueryProxy();
            getLogger().debug("NhinDocQueryStrategyImpl_g1::executeStrategy sending nhin doc query request to "
                    + " target hcid=" + message.getTarget().getHomeCommunity().getHomeCommunityId()
                    + " at url=" + message.getTarget().getUrl());

            message.setResponse(proxy.respondingGatewayCrossGatewayQuery(
                    message.getRequest(), message.getAssertion(), message.getTarget()));

            getLogger().debug("NhinDocQueryStrategyImpl_g1::executeStrategy returning response");
        }catch(Exception ex){
            String err = ExecutorServiceHelper.getFormattedExceptionInfo(ex, message.getTarget(),
                    message.getServiceName());
            OutboundResponseProcessor processor = message.getResponseProcessor();
            message.setResponse(((OutboundDocQueryOrchestratable_a1)processor.
                    processErrorResponse(message, err)).getResponse());
            getLogger().debug("NhinDocQueryStrategyImpl_g1::executeStrategy returning error response");
        }
        auditResponseMessage(message.getResponse(), NhincConstants.AUDIT_LOG_INBOUND_DIRECTION,
                NhincConstants.AUDIT_LOG_NHIN_INTERFACE,
                message.getAssertion(), message.getTarget().getHomeCommunity().getHomeCommunityId());
    }
}
