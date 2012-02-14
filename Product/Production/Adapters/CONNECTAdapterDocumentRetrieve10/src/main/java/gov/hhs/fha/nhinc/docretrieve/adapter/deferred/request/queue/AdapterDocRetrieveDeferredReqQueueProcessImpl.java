/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *
 */
package gov.hhs.fha.nhinc.docretrieve.adapter.deferred.request.queue;

import gov.hhs.fha.nhinc.gateway.adapterdocretrievereqqueueprocess.DocRetrieveDeferredReqQueueProcessRequestType;
import gov.hhs.fha.nhinc.gateway.adapterdocretrievereqqueueprocess.DocRetrieveDeferredReqQueueProcessResponseType;
import gov.hhs.fha.nhinc.gateway.adapterdocretrievereqqueueprocess.SuccessOrFailType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.healthit.nhin.DocRetrieveAcknowledgementType;
import javax.xml.ws.WebServiceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author richard.ettema
 */
public class AdapterDocRetrieveDeferredReqQueueProcessImpl {

    private static Log log = LogFactory.getLog(AdapterDocRetrieveDeferredReqQueueProcessImpl.class);

    protected AdapterDocRetrieveDeferredReqQueueProcessOrchImpl getAdapterDocRetrieveDeferredReqQueueProcessOrchImpl() {
        return new AdapterDocRetrieveDeferredReqQueueProcessOrchImpl();
    }

    /**
     * processDocRetrieveDeferredReqQueue Implementation method for processing request queues on reponding gateway
     * 
     * @param request
     * @param context
     * @return DocRetrieveDeferredReqQueueProcessResponseType
     */
    public DocRetrieveDeferredReqQueueProcessResponseType processDocRetrieveDeferredReqQueue(
            DocRetrieveDeferredReqQueueProcessRequestType request, WebServiceContext context) {

        DocRetrieveDeferredReqQueueProcessResponseType response = new DocRetrieveDeferredReqQueueProcessResponseType();
        SuccessOrFailType sof = new SuccessOrFailType();
        sof.setSuccess(Boolean.FALSE);
        response.setSuccessOrFail(sof);

        DocRetrieveAcknowledgementType docRetrieveAck = new DocRetrieveAcknowledgementType();
        AdapterDocRetrieveDeferredReqQueueProcessOrchImpl adapterDocRetrieveDeferredReqQueueProcessOrchImpl = getAdapterDocRetrieveDeferredReqQueueProcessOrchImpl();
        docRetrieveAck = adapterDocRetrieveDeferredReqQueueProcessOrchImpl.processDocRetrieveDeferredReqQueue(request
                .getMessageId());

        if (docRetrieveAck != null
                && docRetrieveAck.getMessage() != null
                && docRetrieveAck.getMessage().getStatus() != null
                && docRetrieveAck.getMessage().getStatus()
                        .equals(NhincConstants.DOC_RETRIEVE_DEFERRED_RESP_ACK_STATUS_MSG)) {
            sof.setSuccess(Boolean.TRUE);
        }

        return response;
    }
}
