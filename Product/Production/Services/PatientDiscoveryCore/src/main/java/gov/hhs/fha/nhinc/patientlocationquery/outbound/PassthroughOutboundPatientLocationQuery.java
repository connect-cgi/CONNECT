/*
 * Copyright (c) 2009-2018, United States Government, as represented by the Secretary of Health and Human Services.
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
package gov.hhs.fha.nhinc.patientlocationquery.outbound;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayPatientLocationQueryResponseType;
import gov.hhs.fha.nhinc.patientlocationquery.entity.OutboundPatientLocationQueryDelegate;
import gov.hhs.fha.nhinc.patientlocationquery.entity.OutboundPatientLocationQueryOrchestratable;
import ihe.iti.xcpd._2009.PatientLocationQueryRequestType;
import ihe.iti.xcpd._2009.PatientLocationQueryResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tjafri
 */
public class PassthroughOutboundPatientLocationQuery implements OutboundPatientLocationQuery {

    private static final Logger LOG = LoggerFactory.getLogger(PassthroughOutboundPatientLocationQuery.class);

    @Override
    public RespondingGatewayPatientLocationQueryResponseType processPatientLocationQuery(
        PatientLocationQueryRequestType request, AssertionType assertion, NhinTargetCommunitiesType target) {
        //Future Story: audit request
        return sendToNhinProxy(request, assertion, target);
    }

    protected RespondingGatewayPatientLocationQueryResponseType sendToNhinProxy(PatientLocationQueryRequestType request,
        AssertionType assertion, NhinTargetCommunitiesType target) {

        OutboundPatientLocationQueryDelegate ddsDelegate = new OutboundPatientLocationQueryDelegate();
        OutboundPatientLocationQueryOrchestratable ddsOrchestratable = createOrchestratable(ddsDelegate, request,assertion, target);

        PatientLocationQueryResponseType response = ((OutboundPatientLocationQueryOrchestratable) ddsDelegate.process(ddsOrchestratable)).getResponse();

        RespondingGatewayPatientLocationQueryResponseType responseType = new RespondingGatewayPatientLocationQueryResponseType();
        responseType.setPatientLocationQueryResponse(response);
        return responseType;

    }

    protected OutboundPatientLocationQueryOrchestratable createOrchestratable(OutboundPatientLocationQueryDelegate delegate,
        PatientLocationQueryRequestType request, AssertionType assertion, NhinTargetCommunitiesType target) {

        OutboundPatientLocationQueryOrchestratable ddsOrchestratable = new OutboundPatientLocationQueryOrchestratable(delegate);
        ddsOrchestratable.setAssertion(assertion);
        ddsOrchestratable.setRequest(request);
        ddsOrchestratable.setTarget(target);
        return ddsOrchestratable;
    }
}
