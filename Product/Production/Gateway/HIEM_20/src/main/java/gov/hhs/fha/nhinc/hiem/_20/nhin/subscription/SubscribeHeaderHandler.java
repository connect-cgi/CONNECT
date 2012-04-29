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
package gov.hhs.fha.nhinc.hiem._20.nhin.subscription;

import gov.hhs.fha.nhinc.callback.SOAPHeaderHandler;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * 
 * 
 * @author Neil Webb
 */
public class SubscribeHeaderHandler implements SOAPHandler<SOAPMessageContext> {
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(SubscribeHeaderHandler.class);

    @SuppressWarnings("unchecked")
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    public boolean handleMessage(SOAPMessageContext context) {
    	SOAPHeaderHandler prefixHandler = new SOAPHeaderHandler();
    	prefixHandler.handleMessage(context);
        extractReferenceParameters(context);
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {
    }

    private void extractReferenceParameters(SOAPMessageContext context) {
        log.debug("******** In handleMessage() *************");
        SOAPMessage soapMessage = null;
        String soapMessageText = null;
        try {
            if (context != null) {
                log.debug("******** Context was not null *************");
                soapMessage = context.getMessage();
                log.debug("******** After getMessage *************");

                if (soapMessage != null) {
                    log.debug("******** Attempting to write out SOAP message *************");
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        soapMessage.writeTo(bos);
                        soapMessageText = bos.toString();
                        log.debug("Captured soap message: " + soapMessageText);
                    } catch (Throwable t) {
                        log.debug("Exception writing out the message");
                        t.printStackTrace();
                    }
                } else {
                    log.debug("SOAPMessage was null");
                }
            } else {
                log.debug("SOAPMessageContext was null.");
            }
        } catch (Throwable t) {
            log.debug("Error logging the SOAP message: " + t.getMessage());
            t.printStackTrace();
        }
        if (soapMessage != null) {
            @SuppressWarnings("unchecked")
            javax.servlet.http.HttpServletRequest servletRequest = (javax.servlet.http.HttpServletRequest) context
                    .get(MessageContext.SERVLET_REQUEST);
            servletRequest.setAttribute(NhincConstants.HIEM_SUBSCRIBE_SOAP_HDR_ATTR_TAG, soapMessage);
        }
    }

}
