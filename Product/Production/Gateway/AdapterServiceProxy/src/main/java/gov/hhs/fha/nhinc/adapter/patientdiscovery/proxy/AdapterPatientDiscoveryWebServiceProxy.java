/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.adapter.patientdiscovery.proxy;

import gov.hhs.fha.nhinc.adapterpatientdiscoverysecured.AdapterPatientDiscoverySecured;
import gov.hhs.fha.nhinc.adapterpatientdiscoverysecured.AdapterPatientDiscoverySecuredPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
/**
 *
 * @author jhoppesc
 */
public class AdapterPatientDiscoveryWebServiceProxy implements AdapterPatientDiscoveryProxy {

    private static Log log = LogFactory.getLog(AdapterPatientDiscoveryWebServiceProxy.class);
    private static AdapterPatientDiscoverySecured service = new AdapterPatientDiscoverySecured();

    public PRPAIN201306UV02 respondingGatewayPRPAIN201305UV02(org.hl7.v3.PRPAIN201305UV02 body, AssertionType assertion,  NhinTargetCommunitiesType target) {
        PRPAIN201306UV02 response = new PRPAIN201306UV02();
        RespondingGatewayPRPAIN201305UV02RequestType request = new RespondingGatewayPRPAIN201305UV02RequestType();

        request.setAssertion(assertion);
        request.setPRPAIN201305UV02(body);
        request.setNhinTargetCommunities(target);
        
        // Get the URL to the Adapter Subject Discovery
        String url = getUrl();

        if (NullChecker.isNotNullish(url) && (request != null))
        {
            AdapterPatientDiscoverySecuredPortType port = getPort(url, assertion);
            response = port.respondingGatewayPRPAIN201305UV02(request);
        }

        return response;
    }

    private AdapterPatientDiscoverySecuredPortType getPort(String url, AssertionType assertion)
    {
        AdapterPatientDiscoverySecuredPortType port = service.getAdapterPatientDiscoverySecuredPortSoap();

        log.info("Setting endpoint address to Adapter Subject Discovery Secured Service to " + url);
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
            url = ConnectionManagerCache.getLocalEndpointURLByServiceName(NhincConstants.ADAPTER_PATIENT_DISCOVERY_SECURED_SERVICE_NAME);
        } catch (ConnectionManagerException ex)
        {
            log.error("Error: Failed to retrieve url for service: " + NhincConstants.ADAPTER_PATIENT_DISCOVERY_SECURED_SERVICE_NAME + " for local home community");
            log.error(ex.getMessage());
        }

        return url;
    }
}
