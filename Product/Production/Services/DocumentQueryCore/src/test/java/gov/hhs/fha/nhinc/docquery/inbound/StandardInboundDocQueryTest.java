/*
 * Copyright (c) 2009-2019, United States Government, as represented by the Secretary of Health and Human Services.
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
package gov.hhs.fha.nhinc.docquery.inbound;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.docquery.adapter.proxy.AdapterDocQueryProxy;
import gov.hhs.fha.nhinc.docquery.adapter.proxy.AdapterDocQueryProxyObjectFactory;
import gov.hhs.fha.nhinc.docquery.audit.transform.DocQueryAuditTransforms;
import gov.hhs.fha.nhinc.docquery.deferredresponse.adapter.proxy.AdapterDeferredResponseOptionProxy;
import gov.hhs.fha.nhinc.document.DocumentConstants;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import java.util.Properties;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author akong
 *
 */
public class StandardInboundDocQueryTest extends InboundDocQueryTest {

    private static final int NUM_TIMES_TO_INVOKE_ADAPTER_AUDIT = 1;

    @Test
    public void hasInboundProcessingEvent() throws Exception {
        hasInboundProcessingEvent(StandardInboundDocQuery.class);
    }

    @Test
    public void standardInboundDocQueryOrgHcid() {
        standardInboundDocQueryHomeHcid(SENDING_HCID_ORG, SENDING_HCID_ORG_FORMATTED, false);
    }

    @Test
    public void standardInboundDocQueryHomeHcid() {
        standardInboundDocQueryHomeHcid(SENDING_HCID_HOME, SENDING_HCID_HOME_FORMATTED, false);
    }

    @Test
    public void standardInboundDocQueryHomeHcidDeferred() {
        standardInboundDocQueryHomeHcid(SENDING_HCID_HOME, SENDING_HCID_HOME_FORMATTED, true);
    }

    private void standardInboundDocQueryHomeHcid(String sendingHcid, String sendingHcidFormatted, boolean deferredFlag) {

        AssertionType mockAssertion = getMockAssertion(sendingHcid);

        final AdapterDeferredResponseOptionProxy mockDeferredProxy = mock(AdapterDeferredResponseOptionProxy.class);
        if(deferredFlag){
            when(mockAssertion.getDeferredResponseEndpoint()).thenReturn("http://deferredEnpoint/");
            RegistryResponseType mockResponse = mock(RegistryResponseType.class);
            when(mockDeferredProxy.processRequest(Mockito.any(AdhocQueryRequest.class), Mockito.any(AssertionType.class)))
            .thenReturn(mockResponse);
            when(mockResponse.getStatus()).thenReturn(DocumentConstants.XDS_QUERY_RESPONSE_STATUS_SUCCESS);
        }

        StandardInboundDocQuery standardDocQuery = new StandardInboundDocQuery(policyChecker,
            getMockAdapterFactory(mockAssertion), getAuditLogger(true)) {
            @Override
            protected String getLocalHomeCommunityId() {
                return RESPONDING_HCID_FORMATTED;
            }
            @Override
            protected AdapterDeferredResponseOptionProxy getAdapterDeferredProxy() {
                return mockDeferredProxy;
            }
        };

        when(policyChecker.checkIncomingPolicy(request, mockAssertion)).thenReturn(true);

        verifyInboundDocQuery(mockAssertion, sendingHcidFormatted, standardDocQuery, NUM_TIMES_TO_INVOKE_ADAPTER_AUDIT);

        int deferredCall = 0;
        if (deferredFlag) {
            deferredCall = 1;
        }
        verify(mockDeferredProxy, times(deferredCall)).processRequest(Mockito.any(AdhocQueryRequest.class),
            Mockito.any(AssertionType.class));
    }

    @Test
    public void failedPolicy() {
        AssertionType assertion = new AssertionType();
        Properties webContextProperties = new Properties();
        AdapterDocQueryProxyObjectFactory mockAdapterFactory = mock(AdapterDocQueryProxyObjectFactory.class);
        AdapterDocQueryProxy mockAdapterProxy = mock(AdapterDocQueryProxy.class);

        when(mockAdapterFactory.getAdapterDocQueryProxy()).thenReturn(mockAdapterProxy);

        when(policyChecker.checkIncomingPolicy(request, assertion)).thenReturn(false);

        StandardInboundDocQuery standardDocQuery = new StandardInboundDocQuery(policyChecker, mockAdapterFactory,
            getAuditLogger(true)) {
            @Override
            protected String getLocalHomeCommunityId() {
                return RESPONDING_HCID_FORMATTED;
            }
        };
        AdhocQueryResponse actualResponse = standardDocQuery.respondingGatewayCrossGatewayQuery(request, assertion,
            webContextProperties);

        assertEquals(DocumentConstants.XDS_QUERY_RESPONSE_STATUS_FAILURE, actualResponse.getStatus());
        assertEquals(DocumentConstants.XDS_ERRORCODE_REPOSITORY_ERROR,
            actualResponse.getRegistryErrorList().getRegistryError().get(0).getErrorCode());
        assertEquals(NhincConstants.XDS_REGISTRY_ERROR_SEVERITY_ERROR,
            actualResponse.getRegistryErrorList().getRegistryError().get(0).getSeverity());
        verify(mockEJBLogger).auditResponseMessage(eq(request), eq(actualResponse), eq(assertion),
            isNull(NhinTargetSystemType.class), eq(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION),
            eq(NhincConstants.AUDIT_LOG_NHIN_INTERFACE), eq(Boolean.FALSE), eq(webContextProperties),
            eq(NhincConstants.DOC_QUERY_SERVICE_NAME), any(DocQueryAuditTransforms.class));
    }

}
