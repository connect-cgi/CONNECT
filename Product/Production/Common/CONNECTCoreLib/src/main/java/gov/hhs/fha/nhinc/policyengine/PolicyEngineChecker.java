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
package gov.hhs.fha.nhinc.policyengine;

import gov.hhs.fha.nhinc.common.eventcommon.AdhocQueryRequestEventType;
import gov.hhs.fha.nhinc.common.eventcommon.AdhocQueryResultEventType;
import gov.hhs.fha.nhinc.common.eventcommon.DocRetrieveEventType;
import gov.hhs.fha.nhinc.common.eventcommon.FindAuditEventsEventType;
import gov.hhs.fha.nhinc.common.eventcommon.NotifyEventType;
import gov.hhs.fha.nhinc.common.eventcommon.PatDiscReqEventType;
import gov.hhs.fha.nhinc.common.eventcommon.SubscribeEventType;
import gov.hhs.fha.nhinc.common.eventcommon.UnsubscribeEventType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyRequestType;
import gov.hhs.fha.nhinc.transform.policy.PolicyEngineTransformer;
import gov.hhs.fha.nhinc.common.eventcommon.XDREventType;
import gov.hhs.fha.nhinc.common.eventcommon.XDRResponseEventType;

/**
 *
 * @author Jon Hoppesch
 */
public class PolicyEngineChecker {

    /**
     * This method will create the generic Policy Check Request Message from
     * a subject discovery announce request
     *
     * @param request Policy check request message for the subject discovery announce
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyPatDiscRequest(PatDiscReqEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformPatDiscReqToCheckPolicy(request);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * a document query request
     *
     * @param request Policy check request message for the document query
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyAdhocQuery(AdhocQueryRequestEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformAdhocQueryToCheckPolicy(request);
    }

    public CheckPolicyRequestType checkPolicyAdhocQueryResponse(AdhocQueryResultEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformAdhocQueryResultToCheckPolicy(request);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * a document retrieve request
     *
     * @param request Policy check request message for the document retrieve
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyDocRetrieve(DocRetrieveEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformDocRetrieveToCheckPolicy(request);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * an audit query request
     *
     * @param request Policy check request message for the audit query
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyFindAuditEvents(FindAuditEventsEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformFindAuditEventsToCheckPolicy(request);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * a subscribe request
     *
     * @param request Policy check request message for the subscribe
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicySubscribe(SubscribeEventType checkPolicySubscribeRequest) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformSubscribeToCheckPolicy(checkPolicySubscribeRequest);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * an unsubscribe request.
     *
     * @param request Policy check request message for the unsubscribe
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyUnsubscribe(UnsubscribeEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformUnsubscribeToCheckPolicy(request);
    }

    /**
     * This method will create the generic Policy Check Request Message from
     * a notify request
     *
     * @param request Policy check request message for the notify request
     * @return A generic policy check request message that can be passed to the Policy Engine
     */
    public CheckPolicyRequestType checkPolicyNotify(NotifyEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformNotifyToCheckPolicy(request);
    }
    public CheckPolicyRequestType checkPolicyXDRRequest(XDREventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformXDRRequestToCheckPolicy(request);
    }

    public CheckPolicyRequestType checkPolicyXDRResponse(XDRResponseEventType request) {
        PolicyEngineTransformer policyTransformer = new PolicyEngineTransformer();
        return policyTransformer.transformXDRResponseInputToCheckPolicy(request);
    }

}
