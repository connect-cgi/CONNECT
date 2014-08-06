/*
 * Copyright (c) 2014, United States Government, as represented by the Secretary of Health and Human Services.
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
package gov.hhs.fha.nhinc.corex12.docsubmission.realtime.nhin.proxy;

import gov.hhs.fha.nhinc.adaptercore.AdapterCORETransactionPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.corex12.docsubmission.realtime.nhin.proxy.service.NhinCORE_X12DSRealTimeServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClientFactory;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;

import org.apache.log4j.Logger;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeRealTimeRequest;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeRealTimeResponse;

/**
 * @author cmay
 *
 */
public class NhinCORE_X12DSRealTimeProxyWebServiceSecuredImpl implements NhinCORE_X12DSRealTimeProxy {

    private static final Logger LOG = Logger.getLogger(NhinCORE_X12DSRealTimeProxyWebServiceSecuredImpl.class);
    private WebServiceProxyHelper proxyHelper = null;

    public NhinCORE_X12DSRealTimeProxyWebServiceSecuredImpl() {
        proxyHelper = new WebServiceProxyHelper();
    }

    protected CONNECTClient<AdapterCORETransactionPortType> getCONNECTClientSecured(
        ServicePortDescriptor<AdapterCORETransactionPortType> portDescriptor, AssertionType assertion, String url,
        String targetHomeCommunityId, String serviceName) {

        return CONNECTClientFactory.getInstance().getCONNECTClientSecured(portDescriptor, assertion, url,
            targetHomeCommunityId, serviceName);
    }

    @Override
    public COREEnvelopeRealTimeResponse realTimeRequest(COREEnvelopeRealTimeRequest msg, AssertionType assertion,
        NhinTargetSystemType targetSystem, NhincConstants.GATEWAY_API_LEVEL apiLevel) {

        COREEnvelopeRealTimeResponse response;

        try {
            String url = proxyHelper.getUrlFromTargetSystemByGatewayAPILevel(targetSystem,
                NhincConstants.NHIN_CORE_X12DS_REALTIME_SECURED_SERVICE_NAME, apiLevel);

            ServicePortDescriptor<AdapterCORETransactionPortType> portDescriptor = new NhinCORE_X12DSRealTimeServicePortDescriptor();

            CONNECTClient<AdapterCORETransactionPortType> client = getCONNECTClientSecured(portDescriptor, assertion,
                url, targetSystem.getHomeCommunity().getHomeCommunityId(), NhincConstants.NHIN_CORE_X12DS_REALTIME_SECURED_SERVICE_NAME);
            client.enableMtom();

            response = (COREEnvelopeRealTimeResponse) client.invokePort(AdapterCORETransactionPortType.class,
                "realTimeRequest", msg);
        } catch (Exception ex) {
            // TODO: We need to add error handling here based on CORE X12 DS RealTime use cases
            // e.g., Adapter not found, timeout, etc.
            LOG.error("Error calling realTimeRequest: " + ex.getMessage(), ex);
            response = new COREEnvelopeRealTimeResponse();

            response.setErrorMessage(NhincConstants.CORE_X12DS_ACK_ERROR_MSG);
            response.setErrorCode(NhincConstants.CORE_X12DS_ACK_ERROR_CODE);
        }

        return response;
    }
}
