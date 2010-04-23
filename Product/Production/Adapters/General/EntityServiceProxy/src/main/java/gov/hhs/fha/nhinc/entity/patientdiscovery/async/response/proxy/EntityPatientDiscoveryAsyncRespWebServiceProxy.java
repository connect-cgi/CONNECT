/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.entity.patientdiscovery.async.response.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;
import gov.hhs.fha.nhinc.entitypatientdiscoverysecuredasyncresp.EntityPatientDiscoverySecuredAsyncResp;
import gov.hhs.fha.nhinc.entitypatientdiscoverysecuredasyncresp.EntityPatientDiscoverySecuredAsyncRespPortType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02RequestType;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02SecuredRequestType;

/**
 *
 * @author JHOPPESC
 */
public class EntityPatientDiscoveryAsyncRespWebServiceProxy implements EntityPatientDiscoveryAsyncRespProxy {
    private static Log log = LogFactory.getLog(EntityPatientDiscoveryAsyncRespWebServiceProxy.class);
    private static EntityPatientDiscoverySecuredAsyncResp service = new EntityPatientDiscoverySecuredAsyncResp();

    public MCCIIN000002UV01 processPatientDiscoveryAsyncResp(RespondingGatewayPRPAIN201306UV02RequestType request) {
        MCCIIN000002UV01 response = new MCCIIN000002UV01();
        RespondingGatewayPRPAIN201306UV02SecuredRequestType securedRequest = new RespondingGatewayPRPAIN201306UV02SecuredRequestType();
        securedRequest.setNhinTargetCommunities(request.getNhinTargetCommunities());
        securedRequest.setPRPAIN201306UV02(request.getPRPAIN201306UV02());


        // Get the URL to the Entity Patient Discovery Async Req Queue
        String url = getUrl();

        if (NullChecker.isNotNullish(url) && (request != null))
        {
            EntityPatientDiscoverySecuredAsyncRespPortType port = getPort(url, request.getAssertion());
            response = port.processPatientDiscoveryAsyncResp(securedRequest);
        }

        return response;
    }

    private EntityPatientDiscoverySecuredAsyncRespPortType getPort(String url, AssertionType assertion)
    {
        EntityPatientDiscoverySecuredAsyncRespPortType port = service.getEntityPatientDiscoverySecuredAsyncRespPortSoap();

        log.info("Setting endpoint address to Entity Patient Discovery Async Request Secured Service to " + url);
        ((BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        SamlTokenCreator tokenCreator = new SamlTokenCreator();
        Map samlMap = tokenCreator.CreateRequestContext(assertion, url, NhincConstants.PATIENT_DISCOVERY_ACTION);

        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.putAll(samlMap);

        return port;
    }

    private String getUrl() {
        String url = null;

        try
        {
            url = ConnectionManagerCache.getLocalEndpointURLByServiceName(NhincConstants.PATIENT_DISCOVERY_ENTITY_SECURED_ASYNC_RESP_SERVICE_NAME);
        } catch (ConnectionManagerException ex)
        {
            log.error("Error: Failed to retrieve url for service: " + NhincConstants.PATIENT_DISCOVERY_ENTITY_SECURED_ASYNC_RESP_SERVICE_NAME + " for local home community");
            log.error(ex.getMessage());
        }

        return url;
    }

}
