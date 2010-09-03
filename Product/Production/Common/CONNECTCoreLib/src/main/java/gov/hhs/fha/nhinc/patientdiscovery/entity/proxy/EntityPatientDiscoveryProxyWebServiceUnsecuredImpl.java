/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.patientdiscovery.entity.proxy ;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;
import gov.hhs.fha.nhinc.entitypatientdiscovery.EntityPatientDiscoveryPortType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;

/**
 *
 * @author Neil Webb
 */
public class EntityPatientDiscoveryProxyWebServiceUnsecuredImpl implements EntityPatientDiscoveryProxy
{
    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:gov:hhs:fha:nhinc:entitypatientdiscovery";
    private static final String SERVICE_LOCAL_PART = "EntityPatientDiscovery";
    private static final String PORT_LOCAL_PART = "EntityPatientDiscoveryPortSoap";
    private static final String WSDL_FILE = "EntityPatientDiscovery.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:gov:hhs:fha:nhinc:entitypatientdiscovery:RespondingGateway_PRPA_IN201305UV02RequestMessage";

    private WebServiceProxyHelper oProxyHelper = null;

    public EntityPatientDiscoveryProxyWebServiceUnsecuredImpl()
    {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger()
    {
        return LogFactory.getLog(getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper()
    {
        return new WebServiceProxyHelper();
    }

    protected String invokeConnectionManager(String serviceName) throws ConnectionManagerException
    {
        return ConnectionManagerCache.getLocalEndpointURLByServiceName(serviceName);
    }

    protected String getEndpointURL()
    {
        String endpointURL = null;
        String serviceName = NhincConstants.ENTITY_PATIENT_DISCOVERY_SERVICE_NAME;
        try
        {
            endpointURL = invokeConnectionManager(serviceName);
            log.debug("Retrieved endpoint URL for service " + serviceName + ": " + endpointURL);
        }
        catch (ConnectionManagerException ex)
        {
            log.error("Error getting url for " + serviceName + " from the connection manager. Error: " + ex.getMessage(), ex);
        }

        return endpointURL;
    }

    /**
     * Retrieve the service class for this web service.
     *
     * @return The service class for this web service.
     */

    public RespondingGatewayPRPAIN201306UV02ResponseType respondingGatewayPRPAIN201305UV02(PRPAIN201305UV02 pdRequest, AssertionType assertion, NhinTargetCommunitiesType targetCommunities)
    {
        log.debug("Begin respondingGatewayPRPAIN201305UV02");
        RespondingGatewayPRPAIN201306UV02ResponseType response = null;

        try
        {
            String url = getEndpointURL();
            EntityPatientDiscoveryPortType port = getPort(url, NhincConstants.ENTITY_PATIENT_DISCOVERY_SERVICE_NAME, WS_ADDRESSING_ACTION, assertion);

            if(pdRequest == null)
            {
                log.error("PRPAIN201305UV02 was null");
            }
            else if(assertion == null)
            {
                log.error("AssertionType was null");
            }
            else if(targetCommunities == null)
            {
                log.error("NhinTargetCommunitiesType was null");
            }
            else if(port == null)
            {
                log.error("EntityPatientDiscoverySecuredPortType was null");
            }
            else
            {
                RespondingGatewayPRPAIN201305UV02RequestType request = new RespondingGatewayPRPAIN201305UV02RequestType();
                request.setPRPAIN201305UV02(pdRequest);
                request.setAssertion(assertion);
                request.setNhinTargetCommunities(targetCommunities);

                response = (RespondingGatewayPRPAIN201306UV02ResponseType)oProxyHelper.invokePort(port, EntityPatientDiscoveryPortType.class, "respondingGatewayPRPAIN201305UV02", request);
            }
        }
        catch (Exception ex)
        {
            log.error("Error calling respondingGatewayPRPAIN201305UV02: " + ex.getMessage(), ex);
        }

        log.debug("End respondingGatewayPRPAIN201305UV02");
        return response;
    }
    /**
     * This method retrieves and initializes the port.
     *
     * @param url The URL for the web service.
     * @return The port object for the web service.
     */
    protected EntityPatientDiscoveryPortType getPort(String url, String serviceAction, String wsAddressingAction, AssertionType assertion) {
        EntityPatientDiscoveryPortType port = null;
        Service service = getService();
        if (service != null) {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART), EntityPatientDiscoveryPortType.class);
            oProxyHelper.initializeUnsecurePort((javax.xml.ws.BindingProvider) port, url, wsAddressingAction, assertion);
        } else {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    /**
     * Retrieve the service class for this web service.
     *
     * @return The service class for this web service.
     */
    protected Service getService() {
        if (cachedService == null) {
            try {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            } catch (Throwable t) {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

}
