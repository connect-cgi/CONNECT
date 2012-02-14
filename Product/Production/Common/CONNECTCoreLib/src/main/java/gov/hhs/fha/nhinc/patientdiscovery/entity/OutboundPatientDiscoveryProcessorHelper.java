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
package gov.hhs.fha.nhinc.patientdiscovery.entity;

import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Helper methods for PD Processing to create a new cumulativeResponse object for a particular spec level and to
 * transform an individualResponse object from one spec to another
 * 
 * @author paul.eftis
 */
public class OutboundPatientDiscoveryProcessorHelper {

    private static Log log = LogFactory.getLog(OutboundPatientDiscoveryProcessorHelper.class);

    /**
     * constructs a new OutboundPatientDiscoveryOrchestratable_a0 object with associated new cumulativeResponse
     * 
     * @param request
     * @return OutboundPatientDiscoveryOrchestratable_a0
     */
    public static OutboundPatientDiscoveryOrchestratable_a0 createNewCumulativeResponse_a0(
            OutboundPatientDiscoveryOrchestratable request) {

        OutboundPatientDiscoveryOrchestratable_a0 cumulativeResponse = new OutboundPatientDiscoveryOrchestratable_a0(
                null, null, null, null, request.getAssertion(), request.getServiceName(), request.getTarget(),
                request.getRequest());

        // create new cumulativeResponse object
        RespondingGatewayPRPAIN201306UV02ResponseType newResponse = new RespondingGatewayPRPAIN201306UV02ResponseType();
        cumulativeResponse.setCumulativeResponse(newResponse);
        log.debug("EntityPatientDiscoveryProcessorHelper constructed initial a0 cumulativeResponse");
        return cumulativeResponse;
    }

    /**
     * constructs a new OutboundPatientDiscoveryOrchestratable_a1 object with associated new cumulativeResponse
     * 
     * @param request
     * @return OutboundPatientDiscoveryOrchestratable_a1
     */
    public static OutboundPatientDiscoveryOrchestratable_a1 createNewCumulativeResponse_a1(
            OutboundPatientDiscoveryOrchestratable request) {

        OutboundPatientDiscoveryOrchestratable_a1 cumulativeResponse = new OutboundPatientDiscoveryOrchestratable_a1(
                null, null, null, null, request.getAssertion(), request.getServiceName(), request.getTarget(),
                request.getRequest());

        // create new cumulativeResponse object
        RespondingGatewayPRPAIN201306UV02ResponseType newResponse = new RespondingGatewayPRPAIN201306UV02ResponseType();
        cumulativeResponse.setCumulativeResponse(newResponse);
        log.debug("EntityPatientDiscoveryProcessorHelper constructed initial a1 cumulativeResponse");
        return cumulativeResponse;
    }

    /**
     * takes a response spec a1 and converts to response spec a0
     * 
     * @param original is spec a1
     * @return OutboundPatientDiscoveryOrchestratable_a0 with transformed a0 response
     */
    public static OutboundPatientDiscoveryOrchestratable_a0 transformResponse_ToA0(
            OutboundPatientDiscoveryOrchestratable original) {

        // currently a0 is same as a1
        OutboundPatientDiscoveryOrchestratable_a0 response_a0 = new OutboundPatientDiscoveryOrchestratable_a0(null,
                null, null, null, original.getAssertion(), original.getServiceName(), original.getTarget(),
                original.getRequest());

        OutboundPatientDiscoveryOrchestratable_a1 original_a1 = (OutboundPatientDiscoveryOrchestratable_a1) original;
        response_a0.setResponse(original_a1.getResponse());
        return response_a0;
    }

    /**
     * takes a response spec a0 and converts to response spec a1 Currently PD response is same for both specs
     * 
     * @param original is spec a0
     * @return OutboundPatientDiscoveryOrchestratable_a1 with transformed a1 response
     */
    public static OutboundPatientDiscoveryOrchestratable_a1 transformResponse_ToA1(
            OutboundPatientDiscoveryOrchestratable original) {

        // currently a0 is same as a1
        OutboundPatientDiscoveryOrchestratable_a1 response_a1 = new OutboundPatientDiscoveryOrchestratable_a1(null,
                null, null, null, original.getAssertion(), original.getServiceName(), original.getTarget(),
                original.getRequest());

        OutboundPatientDiscoveryOrchestratable_a0 original_a0 = (OutboundPatientDiscoveryOrchestratable_a0) original;
        response_a1.setResponse(original_a0.getResponse());
        return response_a1;
    }
}
