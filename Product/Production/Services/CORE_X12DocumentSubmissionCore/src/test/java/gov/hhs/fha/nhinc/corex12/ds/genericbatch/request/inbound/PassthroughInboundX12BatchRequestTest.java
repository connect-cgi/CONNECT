/*
 * Copyright (c) 2009-2015, United States Government, as represented by the Secretary of Health and Human Services.
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
package gov.hhs.fha.nhinc.corex12.ds.genericbatch.request.inbound;

import gov.hhs.fha.nhinc.audit.ejb.AuditEJBLogger;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.corex12.ds.audit.X12BatchAuditLogger;
import gov.hhs.fha.nhinc.corex12.ds.audit.transform.X12BatchAuditTransforms;
import gov.hhs.fha.nhinc.corex12.ds.genericbatch.request.adapter.proxy.AdapterX12BatchRequestProxyObjectFactory;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import java.util.Properties;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmission;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmissionResponse;
import org.junit.Test;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import gov.hhs.fha.nhinc.corex12.ds.genericbatch.common.adapter.proxy.AdapterX12BatchProxy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author tjafri
 */
public class PassthroughInboundX12BatchRequestTest {

    private final AuditEJBLogger mockEJBLogger = mock(AuditEJBLogger.class);
    private final COREEnvelopeBatchSubmission request = new COREEnvelopeBatchSubmission();
    private final AssertionType assertion = new AssertionType();
    private final Properties webContextProperties = new Properties();
    private final AdapterX12BatchRequestProxyObjectFactory adpFactory
        = mock(AdapterX12BatchRequestProxyObjectFactory.class);
    private final AdapterX12BatchProxy reqProxy = mock(AdapterX12BatchProxy.class);
    private final COREEnvelopeBatchSubmissionResponse expectedResponse = new COREEnvelopeBatchSubmissionResponse();
    private COREEnvelopeBatchSubmissionResponse actualResposne = null;
    private PassthroughInboundX12BatchRequest batchReq = null;

    @Test
    public void auditLoggingOnForInboundX12BatchRequest() {
        when(adpFactory.getAdapterCORE_X12DocSubmissionProxy()).thenReturn(reqProxy);
        when(reqProxy.batchSubmitTransaction(request, assertion)).thenReturn(expectedResponse);
        batchReq = new PassthroughInboundX12BatchRequest(adpFactory, getAuditLogger(true));
        actualResposne = batchReq.batchSubmitTransaction(request, assertion, webContextProperties);
        verify(mockEJBLogger).auditResponseMessage(eq(request), eq(actualResposne),
            eq(assertion), isNull(NhinTargetSystemType.class), eq(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION),
            eq(NhincConstants.AUDIT_LOG_NHIN_INTERFACE), eq(Boolean.FALSE), eq(webContextProperties),
            eq(NhincConstants.CORE_X12DS_GENERICBATCH_REQUEST_SERVICE_NAME),
            any(X12BatchAuditTransforms.class));
    }

    @Test
    public void auditLoggingOffForInboundX12BatchRequest() {
        when(adpFactory.getAdapterCORE_X12DocSubmissionProxy()).thenReturn(reqProxy);
        when(reqProxy.batchSubmitTransaction(request, assertion)).thenReturn(expectedResponse);
        batchReq = new PassthroughInboundX12BatchRequest(adpFactory, getAuditLogger(false));
        actualResposne = batchReq.batchSubmitTransaction(request, assertion, webContextProperties);
        verify(mockEJBLogger, never()).auditResponseMessage(eq(request), eq(actualResposne),
            eq(assertion), isNull(NhinTargetSystemType.class), eq(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION),
            eq(NhincConstants.AUDIT_LOG_NHIN_INTERFACE), eq(Boolean.FALSE), eq(webContextProperties),
            eq(NhincConstants.CORE_X12DS_GENERICBATCH_REQUEST_SERVICE_NAME),
            any(X12BatchAuditTransforms.class));
    }

    private X12BatchAuditLogger getAuditLogger(final boolean isLoggingOn) {
        return new X12BatchAuditLogger() {
            @Override
            protected AuditEJBLogger getAuditLogger() {
                return mockEJBLogger;
            }

            @Override
            protected boolean isAuditLoggingOn(String serviceName) {
                return isLoggingOn;
            }
        };
    }
}
