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
package gov.hhs.fha.nhinc.auditquerylog;

import gov.hhs.fha.nhinc.audit.retrieve.AuditRetrieveEventsUtil;
import gov.hhs.fha.nhinc.auditrepository.hibernate.AuditRepositoryDAO;
import gov.hhs.fha.nhinc.common.auditquerylog.EventTypeList;
import gov.hhs.fha.nhinc.common.auditquerylog.QueryAuditEventsBlobRequest;
import gov.hhs.fha.nhinc.common.auditquerylog.QueryAuditEventsBlobResponse;
import gov.hhs.fha.nhinc.common.auditquerylog.QueryAuditEventsRequestType;
import gov.hhs.fha.nhinc.common.auditquerylog.QueryAuditEventsResponseType;
import gov.hhs.fha.nhinc.common.auditquerylog.QueryAuditEventsRequestByRequestMessageId;
import gov.hhs.fha.nhinc.common.auditquerylog.RemoteHcidList;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author tjafri
 */
public class AuditQueryLogImpl {

    private AuditRepositoryDAO dao;
    private AuditRetrieveEventsUtil resultUtil = null;

    /**
     * constructor. initialize AuditRetrieveEventsUtil to build AuditQueryResponse
     */
    public AuditQueryLogImpl() {
        resultUtil = new AuditRetrieveEventsUtil();
    }

    /**
     *
     * @param request - Audit Search params will be provided by this requestAuditEvents. The Request may have Event
     * startDate, Event EndDate, UserId, Remote Org Id and ServiceName/EventType.These are optional parameters. If none
     * of them is provided all records will be retrieved.
     * @return QueryAuditEventsResponseType - The Response will be having EventType or ServiceName, EventStatus- Success
     * or Failure, Event Timestamp, UserId(Human who initiated transaction), Direction (Outbound/Inbound), MessageID
     * -RequestMessageID, RelatesTo (Relates the DeferredRequests and DeferredResponses, Remote Organization Id and
     * Audit Id.
     *
     * The Adapter logic will be implemented in FHAC-690
     */
    public QueryAuditEventsResponseType queryAuditEvents(QueryAuditEventsRequestType request) {
        return resultUtil.getQueryAuditEventResponse(getAuditRepositoryDao().queryByAuditOptions(
            getEventOutcome(request.getEventOutcomeIndicator()), getEventTypes(request.getEventTypeList()),
            request.getUserId(), getRemoteHcids(request.getRemoteHcidList()), getRequestDate(request.getEventBeginDate()),
            getRequestDate(request.getEventEndDate())));

    }

    /**
     *
     * @param request - Audit search params MessageId and RelatesTo will be provided by Request. These are optional
     * fields in requestAuditEvents
     * @return QueryAuditEventsResponseType - The Response will be having EventType or ServiceName, EventStatus- Success
     * or Failure, Event Timestamp, UserId(Human who initiated transaction), Direction (Outbound/Inbound), MessageID
     * -RequestMessageID, RelatesTo (Relates the DeferredRequests and DeferredResponses, Remote Organization Id and
     * Audit Id.
     *
     */
    public QueryAuditEventsResponseType queryAuditEventsByMessageId(
        QueryAuditEventsRequestByRequestMessageId request) {
        return resultUtil.getQueryAuditEventResponse(getAuditRepositoryDao().queryAuditRecords(
            request.getRequestMessageId(), request.getRelatesTo()));
    }

    /**
     *
     *
     * @param request - Audit search params. Audit Id will be provided by requestAuditEvents.
     * @return QueryAuditEventsBlobResponse - Response will be having only Audit Blob message.
     *
     * The Adapter logic will be implemented in FHAC-690
     */
    public QueryAuditEventsBlobResponse queryAuditEventsById(QueryAuditEventsBlobRequest request) {
        return resultUtil.getQueryAuditEventBlobResponse(getAuditRepositoryDao().queryByAuditId(
            (new Long(request.getId())).intValue()));
    }

    /**
     *
     * @return AuditRepositoryDAO Instance of AuditRepositoryDAO to query audit events.
     */
    protected AuditRepositoryDAO getAuditRepositoryDao() {
        if (dao == null) {
            dao = new AuditRepositoryDAO();
        }
        return dao;
    }

    private Date getRequestDate(XMLGregorianCalendar dateObj) {
        if (dateObj != null) {
            return dateObj.toGregorianCalendar().getTime();
        }
        return null;
    }

    private Integer getEventOutcome(BigInteger outcome) {
        if (outcome != null) {
            return outcome.intValue();
        }
        return null;
    }

    private List<String> getEventTypes(EventTypeList eventList) {
        if (eventList != null) {
            eventList.getEventType();
        }
        return null;
    }

    private List<String> getRemoteHcids(RemoteHcidList remoteHcidList) {
        if (remoteHcidList != null) {
            remoteHcidList.getRemoteHcid();
        }
        return null;
    }

}
