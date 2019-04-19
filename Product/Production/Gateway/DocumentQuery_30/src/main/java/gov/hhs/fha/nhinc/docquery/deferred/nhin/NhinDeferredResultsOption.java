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
package gov.hhs.fha.nhinc.docquery.deferred.nhin;

import gov.hhs.fha.nhinc.docquery.deferred.impl.AdapterResponseHelper;
import gov.hhs.fha.nhinc.dq.nhindeferredresultsecured.NhinDocQueryDeferredResponseSecuredPortType;
import gov.hhs.fha.nhinc.messaging.server.BaseService;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Webservice for processing a DeferredResponseOption's response from the Responding Gateway.
 */
public class NhinDeferredResultsOption extends BaseService implements NhinDocQueryDeferredResponseSecuredPortType {

    private static final Logger LOG = LoggerFactory.getLogger(NhinDeferredResultsOption.class);

    @Resource
    private WebServiceContext context;

    public void setContext(WebServiceContext context) {
        this.context = context;
    }
    public WebServiceContext getContext() {
        return context;
    }

    @Override
    public RegistryResponseType respondingGatewayCrossGatewayQueryDeferredNhinSecured(AdhocQueryResponse message) {
        LOG.debug("Inside Nhin Layer Secured");

        // Look up Request ID is in the "deferredxcarequest" table, and verify if a record exists
            //If not, return a failure message

        // Grab the URL of the document consumer from the table record
        // Forward AdHocQueryResponse to the adapter located at the document consumer's endpoint
        // Return the result of the adapter call here, and reply back to the Responding Gateway with an ACK


        return AdapterResponseHelper.createSuccessResponse();


    }

}
