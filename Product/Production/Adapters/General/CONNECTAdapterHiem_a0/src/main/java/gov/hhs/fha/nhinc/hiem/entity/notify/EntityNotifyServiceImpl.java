/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.hiem.entity.notify;

import gov.hhs.fha.nhinc.common.nhinccommon.AcknowledgementType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.NotifyRequestType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import javax.xml.ws.WebServiceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import javax.xml.ws.BindingProvider;
import gov.hhs.fha.nhinc.entitynotificationconsumersecured.EntityNotificationConsumerSecuredPortType;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * 
 * @author dunnek
 */
public class EntityNotifyServiceImpl {

    private static Log log = LogFactory.getLog(EntityNotifyServiceImpl.class);
    private static Service cachedService = null;
    private static WebServiceProxyHelper oProxyHelper = null;
    private static final String NAMESPACE_URI = "urn:gov:hhs:fha:nhinc:entitynotificationconsumersecured";
    private static final String SERVICE_LOCAL_PART = "EntityNotificationConsumerSecured";
    private static final String PORT_LOCAL_PART = "EntityNotificationConsumerSecuredPortSoap";
    private static final String WSDL_FILE = "EntityNotificationConsumerSecured.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:Notify";

    public AcknowledgementType notify(NotifyRequestType notifyRequest, WebServiceContext context) {
        log.debug("begin notify");
        AcknowledgementType result = null;

        try {
            AssertionType assertIn = notifyRequest.getAssertion();

            String url = getURL();
            EntityNotificationConsumerSecuredPortType port = getPort(url, assertIn);

            // The proxyhelper invocation casts exceptions to generic Exception, trying to use the default method
            // invocation
            result = port.notify(notifyRequest.getNotify());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return result;
    }

    private String getURL() {
        String url = "";

        try {
            url = ConnectionManagerCache.getInstance().getLocalEndpointURLByServiceName(
                    NhincConstants.HIEM_NOTIFY_ENTITY_SERVICE_NAME_SECURED);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return url;
    }

    protected EntityNotificationConsumerSecuredPortType getPort(String url, AssertionType assertIn) {
        EntityNotificationConsumerSecuredPortType oPort = null;
        try {
            Service oService = getService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);

            if (oService != null) {
                log.debug("EntityNotifyServiceImpl Obtained service - creating port.");
                oPort = oService.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART),
                        EntityNotificationConsumerSecuredPortType.class);

                // Initialize secured port
                getWebServiceProxyHelper().initializeSecurePort((BindingProvider) oPort, url,
                        NhincConstants.HIEM_NOTIFY_ENTITY_SERVICE_NAME_SECURED, WS_ADDRESSING_ACTION, assertIn);
            } else {
                log.error("Unable to obtain service - no port created.");
            }
        } catch (Throwable t) {
            log.error("Error creating service: " + t.getMessage(), t);
        }
        return oPort;
    }

    private WebServiceProxyHelper getWebServiceProxyHelper() {
        if (oProxyHelper == null) {
            oProxyHelper = new WebServiceProxyHelper();
        }
        return oProxyHelper;
    }

    private Service getService(String wsdl, String uri, String service) {
        if (cachedService == null) {
            try {
                cachedService = getWebServiceProxyHelper().createService(wsdl, uri, service);
            } catch (Throwable t) {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }
}
