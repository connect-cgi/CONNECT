/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.transform.audit;

import com.services.nhinc.schema.auditmessage.AuditMessageType.ActiveParticipant;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.xml.bind.JAXBContext;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.Marshaller;

import com.services.nhinc.schema.auditmessage.AuditMessageType;
import com.services.nhinc.schema.auditmessage.AuditSourceIdentificationType;
import com.services.nhinc.schema.auditmessage.CodedValueType;
import com.services.nhinc.schema.auditmessage.EventIdentificationType;
import com.services.nhinc.schema.auditmessage.ParticipantObjectIdentificationType;
import gov.hhs.fha.nhinc.common.auditlog.LogSubjectRevisedRequestType;
import gov.hhs.fha.nhinc.common.auditlog.LogNhinSubjectDiscoveryAckRequestType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import org.hl7.v3.PIXConsumerPRPAIN201305UVRequestType;
import org.hl7.v3.PIXConsumerPRPAIN201306UVRequestType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import gov.hhs.fha.nhinc.common.auditlog.LogEventRequestType;
import gov.hhs.fha.nhinc.common.auditlog.LogPatientDiscoveryRequestType;
import gov.hhs.fha.nhinc.common.auditlog.PatientDiscoveryMessageType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import org.hl7.v3.II;
import org.hl7.v3.MCCIMT000300UV01Sender;
import org.hl7.v3.PRPAIN201305UV;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201305UVQUQIMT021001UV01ControlActProcess;
import org.hl7.v3.PRPAIN201306UV;
import org.hl7.v3.PRPAIN201306UVMFMIMT700711UV01ControlActProcess;
import org.hl7.v3.PRPAIN201306UVMFMIMT700711UV01RegistrationEvent;
import org.hl7.v3.PRPAIN201306UVMFMIMT700711UV01Subject1;
import org.hl7.v3.PRPAIN201306UVMFMIMT700711UV01Subject2;
import org.hl7.v3.PRPAMT201306UVLivingSubjectId;
import org.hl7.v3.PRPAMT201306UVParameterList;
import org.hl7.v3.PRPAMT201306UVQueryByParameter;
import org.hl7.v3.PRPAMT201310UVPatient;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;

/**
 *
 * @author shawc
 *
 * Note: requirements for the LogEventRequestType
 * "Direction", required = true
 * "Interface", required = true
 * "EventIdentification", required = true
 * "ActiveParticipant", required = true
 * "AuditSourceIdentification", required = true
 * "ParticipantObjectIdentification", required = false
 */
public class PatientDiscoveryTransforms {

    private Log log = null;//LogFactory.getLog(PatientDiscoveryTransforms.class);

    /**
     * Default Constructor
     */
    public PatientDiscoveryTransforms()
    {
        log = createLogger();
    }

   /**
    * This method tranforms a patient discovery request into an audit log message
    * but it leaves the direction decision needed for the audit log up to the
    * caller.
    *
    * @param message
    * @return
    */
    private LogEventRequestType transformPRPAIN201305RequestToAuditMsg(PRPAIN201305UV oPatientDiscoveryRequestMessage, AssertionType oAssertion)
    {
        LogEventRequestType oReturnLogEventRequestType = null;
        
        AuditMessageType oAuditMessageType = null;
        
        addLogDebug("*********************************************************");
        addLogDebug("Entering transformPRPAIN201305RequestToAuditMsg() method.");
        addLogDebug("*********************************************************");
        
        oAuditMessageType = new AuditMessageType();
        
        // Extract UserInfo from request assertion
        UserType oUserInfo = new UserType();

        if ((oAssertion != null) &&
             (oAssertion.getUserInfo() != null))
        {
            oUserInfo = oAssertion.getUserInfo();
            addLogDebug("Incomming request.getAssertion.getUserInfo.getUserName: " + oUserInfo.getUserName());
            addLogDebug("Incomming request.getAssertion.getUserInfo.getRole: " + oUserInfo.getRole());
        }
        else
        {
            //TODO add a unit test case...
            addLogError("The incomming UserType object or request assertion object containing the assertion user info was null.");
            return null;
        } //else continue

        boolean bRequiredFieldsAreNull = areRequired201305fieldsNull(oPatientDiscoveryRequestMessage);
        if (bRequiredFieldsAreNull) //TODO call method to test to see if all required fields are not null.
        {
            //TODO add a unit test case...
            addLogError("The PRPA_IN201305UV patient discovery object was null.");
            return null;
        } //else continue

        oReturnLogEventRequestType = new LogEventRequestType();
        
        // Create EventIdentification
        CodedValueType eventID = getCodedValueTypeFor201305UV();

        EventIdentificationType oEventIdentificationType = getEventIdentificationType(eventID);
        oAuditMessageType.setEventIdentification(oEventIdentificationType);
        addLogDebug("Audit record EventIdentificationType.getEventActionCode(): " + oEventIdentificationType.getEventActionCode());

        /* Create Active Participant Section */
        ActiveParticipant participant = getActiveParticipant(oUserInfo);
        oAuditMessageType.getActiveParticipant().add(participant);
        addLogDebug("Audit record AuditMessageType.getActiveParticipant().get(0).getUserName(): " + oAuditMessageType.getActiveParticipant().get(0).getUserName());

        /* Assign AuditSourceIdentification */
        String sCommunityId = "";
        String sCommunityName = "";
        String sPatientId = "";
        
        II oII = getHL7IdentifiersFromRequest(oPatientDiscoveryRequestMessage); //null values checked from the earlier call to areRequired201305fieldsNull() method
        sPatientId = oII.getExtension();
        sCommunityId = oII.getRoot();

        sPatientId = getCompositePatientId(sCommunityId, sPatientId);
        addLogDebug("PatientId: " + sPatientId);

        /* Assign ParticipationObjectIdentification */
        ParticipantObjectIdentificationType participantObject = getParticipantObjectIdentificationType(sPatientId);

        //resetting the community name and id information for the audit
        //source identification
        if (oUserInfo != null && oUserInfo.getOrg() != null)
        {
            if (oUserInfo.getOrg().getHomeCommunityId() != null)
            {
                sCommunityId = oUserInfo.getOrg().getHomeCommunityId();
            }
            if (oUserInfo.getOrg().getName() != null)
            {
                sCommunityName = oUserInfo.getOrg().getName();
            }
        }

        /* Create the AuditSourceIdentifierType object */
        AuditSourceIdentificationType auditSource = getAuditSourceIdentificationType(sCommunityId, sCommunityName);
        oAuditMessageType.getAuditSourceIdentification().add(auditSource);
        addLogDebug("Audit record AuditMessageType.getAuditSourceIdentification().get(0).getAuditSourceID(): "
                + oAuditMessageType.getAuditSourceIdentification().get(0).getAuditSourceID());

        // Put the contents of the actual message into the Audit Log Message
        ByteArrayOutputStream baOutStrm = new ByteArrayOutputStream();
        marshalPatientDiscoveryMessage(baOutStrm, oPatientDiscoveryRequestMessage);
        participantObject.setParticipantObjectQuery(baOutStrm.toByteArray());
        oAuditMessageType.getParticipantObjectIdentification().add(participantObject);
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getAuditSourceID(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectID());
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectName(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectName());
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectSensitivity(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectSensitivity());


        oReturnLogEventRequestType.setAuditMessage(oAuditMessageType);
        
        addLogDebug("*********************************************************");
        addLogDebug("Exiting transformPRPAIN201305RequestToAuditMsg() method.");
        addLogDebug("*********************************************************");        

        return oReturnLogEventRequestType;
  
    }

    /**
     * This method translates a patient discovery response to an audit log request.
     * It leave the direction decision needed for the audit log up to the caller.
     *
     * @param oRequest
     * @return
     */
    private LogEventRequestType transformPRPAIN201306ResponseToAuditMsg(PRPAIN201306UV oPatientDiscoveryResponseMessage, AssertionType oAssertion)
    {
        LogEventRequestType oReturnLogEventRequestType = null;
        AuditMessageType oAuditMessageType = null;
//        PRPAIN201306UV oPatientDiscoveryResponseMessage = null;

        addLogInfo("******************************************************************");
        addLogInfo("Entering transformPRPAIN201306ResponseToAuditMsg() method.");
        addLogInfo("******************************************************************");


        // Extract UserInfo from request assertion
        UserType oUserInfo = new UserType();
        if ((oAssertion != null) &&
             (oAssertion.getUserInfo() != null))
        {
            oUserInfo = oAssertion.getUserInfo();
            addLogDebug("Incomming request.getAssertion.getUserInfo.getUserName: " + oUserInfo.getUserName());
            addLogDebug("Incomming request.getAssertion.getUserInfo.getRole: " + oUserInfo.getRole());
        }
        else
        {
            //TODO add a unit test case...
            addLogError("The UserType object or request assertion object containing the assertion user info was null.");
            return null;
        } //else continue

        if (oPatientDiscoveryResponseMessage == null)
        {
            addLogError("The PatientDiscoveryResponseMessage parameter is null.");
            return null;
        } //else continue

        oReturnLogEventRequestType = new LogEventRequestType();

        CodedValueType eventID = getCodedValueTypeFor201306UV();
        EventIdentificationType oEventIdentificationType = getEventIdentificationType(eventID);
        oAuditMessageType.setEventIdentification(oEventIdentificationType);

        // Create Active Participant Section
        ActiveParticipant participant = getActiveParticipant(oUserInfo);
        oAuditMessageType.getActiveParticipant().add(participant);
        addLogDebug("Audit record AuditMessageType.getActiveParticipant().get(0).getUserName(): " + oAuditMessageType.getActiveParticipant().get(0).getUserName());

        /* Assign AuditSourceIdentification */
        String sCommunityId = "";
        String sCommunityName = "";
        String sPatientId = "";
        //get the patient id from the request
        PRPAIN201306UVMFMIMT700711UV01ControlActProcess oControlActProcess = oPatientDiscoveryResponseMessage.getControlActProcess();
        List<PRPAIN201306UVMFMIMT700711UV01Subject1> oSubject1 = oControlActProcess.getSubject();
        PRPAIN201306UVMFMIMT700711UV01RegistrationEvent oRegistrationEvent = oSubject1.get(0).getRegistrationEvent();
        PRPAIN201306UVMFMIMT700711UV01Subject2 oSubject2 = oRegistrationEvent.getSubject1();
        PRPAMT201310UVPatient oPatient = oSubject2.getPatient();
        List<II> oII = oPatient.getId();
        sPatientId = oII.get(0).getExtension();
        sCommunityId = oII.get(0).getRoot();

        sPatientId = getCompositePatientId(sCommunityId, sPatientId);
        addLogDebug("PatientId: " + sPatientId);


        //reset the community id and name to that found on the assertion??
        if (oUserInfo != null &&
                oUserInfo.getOrg() != null) {
            if (oUserInfo.getOrg().getHomeCommunityId() != null) {
                sCommunityId = oUserInfo.getOrg().getHomeCommunityId();
            }
            if (oUserInfo.getOrg().getName() != null) {
                sCommunityName = oUserInfo.getOrg().getName();
            }
        }

        AuditSourceIdentificationType oAuditSource = getAuditSourceIdentificationType(sCommunityId, sCommunityName);
        oAuditMessageType.getAuditSourceIdentification().add(oAuditSource);
        addLogDebug("Audit record AuditMessageType.getAuditSourceIdentification().get(0).getAuditSourceID(): "
                + oAuditMessageType.getAuditSourceIdentification().get(0).getAuditSourceID());

        /* Assign ParticipationObjectIdentification */
        ParticipantObjectIdentificationType participantObject = getParticipantObjectIdentificationType(sPatientId);

        // Put the contents of the actual message into the Audit Log Message
        ByteArrayOutputStream baOutStrm = new ByteArrayOutputStream();
        marshalPatientDiscoveryMessage(baOutStrm, oPatientDiscoveryResponseMessage);
        participantObject.setParticipantObjectQuery(baOutStrm.toByteArray());
        oAuditMessageType.getParticipantObjectIdentification().add(participantObject);
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getAuditSourceID(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectID());
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectName(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectName());
        addLogDebug("Audit record AuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectSensitivity(): "
                + oAuditMessageType.getParticipantObjectIdentification().get(0).getParticipantObjectSensitivity());

        addLogInfo("******************************************************************");
        addLogInfo("Exiting transformPRPAIN201306ResponseToAuditMsg() method.");
        addLogInfo("******************************************************************");

        oReturnLogEventRequestType.setAuditMessage(oAuditMessageType);

        return oReturnLogEventRequestType;
    }

    /**
     * this method tranforms a patient discovery request from an inbound entity into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformEntityPRPAIN201305RequestToAuditMsg(RespondingGatewayPRPAIN201305UV02RequestType oRequest, AssertionType oAssertion)
    {
        LogEventRequestType oReturnLogEventRequestType = null;
        PRPAIN201305UV oPatientDiscoveryRequestMessage = null;
//        AssertionType oAssertion = null;

        addLogInfo("***************************************************************");
        addLogInfo("Entering transformEntityPRPAIN201305RequestToAuditMsg() method.");
        addLogInfo("***************************************************************");

        if (oRequest == null)
        {
            addLogError("The incomming Patient Discovery request message was null.");
            return null;
        }
        else
        {
            oPatientDiscoveryRequestMessage = oRequest.getPRPAIN201305UV();
//            oAssertion = oRequest.getAssertion();
        }

        if ((oPatientDiscoveryRequestMessage == null) && (oAssertion == null))
        {
            addLogError("The Patient Discovery request did not have a PRPAIN201305UV object or an AssertionType object.");
            return null;
        }
        else
        {
            oReturnLogEventRequestType = transformPRPAIN201305RequestToAuditMsg(oPatientDiscoveryRequestMessage, oAssertion);
        }

        if (oReturnLogEventRequestType == null)
        {
            addLogError("There was a problem translating the request into an audit log request object.");
            oReturnLogEventRequestType = null;
        }
        else
        {
            oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION);
            oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_ENTITY_INTERFACE); 
        }

        addLogInfo("**************************************************************");
        addLogInfo("Exiting transformEntityPRPAIN201305RequestToAuditMsg() method.");
        addLogInfo("**************************************************************");

        return oReturnLogEventRequestType;

    }

    /**
     * this method tranforms a patient discovery request from an inbound NHIN into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformNhinPRPAIN201305RequestToAuditMsg(PRPAIN201305UV oRequest, AssertionType oAssertion)
    {
        LogEventRequestType oReturnLogEventRequestType = null;
//        PRPAIN201305UV oPatientDiscoveryRequestMessage = null;
//        AssertionType oAssertion = null;

        addLogInfo("************************************************");
        addLogInfo("Entering transformPRPA201305ToAuditMsg() method.");
        addLogInfo("************************************************");

        if (oRequest == null)
        {
            addLogError("The incomming Patient Discovery request message was null.");
            return null;
        } //else continue

        if (oAssertion == null)
        {
            addLogError("The AssertionType object was null.");
            return null;
        } //else continue

        oReturnLogEventRequestType = transformPRPAIN201305RequestToAuditMsg(oRequest, oAssertion);


        if (oReturnLogEventRequestType == null)
        {
            addLogError("There was a problem translating the request into an audit log request object.");
            return null;
        }
        else
        {
            oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION);
            oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_NHIN_INTERFACE);
        }

        addLogInfo("**************************************************************");
        addLogInfo("Exiting transformEntityPRPAIN201305RequestToAuditMsg() method.");
        addLogInfo("**************************************************************");

        return oReturnLogEventRequestType;

    }

    /**
     * this method tranforms a patient discovery request from an inbound Adapter (pass-through mode) into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformAdapterPRPAIN201305RequestToAuditMsg(PRPAIN201305UV oRequest, AssertionType oAssertion)
    {
        LogEventRequestType oReturnLogEventRequestType = null;


        addLogInfo("************************************************");
        addLogInfo("Entering transformPRPA201305ToAuditMsg() method.");
        addLogInfo("************************************************");

        if (oRequest == null)
        {
            addLogError("The incomming Patient Discovery request message was null.");
            return null;
        } //else continue

        if (oAssertion == null)
        {
            addLogError("The AssertionType object was null.");
            return null;
        } //else continue

        oReturnLogEventRequestType = transformPRPAIN201305RequestToAuditMsg(oRequest, oAssertion);


        if (oReturnLogEventRequestType == null)
        {
            addLogError("There was a problem translating the request into an audit log request object.");
            return null;
        }
        else
        {
            oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_INBOUND_DIRECTION);
            oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_ADAPTER_INTERFACE);
        }

        addLogInfo("**************************************************************");
        addLogInfo("Exiting transformEntityPRPAIN201305RequestToAuditMsg() method.");
        addLogInfo("**************************************************************");

        return oReturnLogEventRequestType;

    }

    /**
     * this method tranforms a patient discovery response for an outbound Entity into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformEntityPRPAIN201306ResponseToAuditMsg(RespondingGatewayPRPAIN201306UV02ResponseType oRequest, AssertionType oAssertion)
    {
          LogEventRequestType oReturnLogEventRequestType = null;
          PRPAIN201306UV oPatientDiscoveryResponseMessage = null;

          addLogInfo("****************************************************************");
          addLogInfo("Entering transformEntityPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("****************************************************************");

          if (oRequest == null)
          {
              addLogError("The Patient Discovery response message was null.");
              return null;
          }
          else
          {
              oPatientDiscoveryResponseMessage = oRequest.getPRPAIN201306UV();
          }

          if (oPatientDiscoveryResponseMessage == null)
          {
                addLogError("The PRPAIN201306UV object from the request was null.");
                return null;
          }

          if (oAssertion == null)
          {
              addLogError("The assertion object parameter was null.");
              return null;
          }
          else
          {
              oReturnLogEventRequestType = transformPRPAIN201306ResponseToAuditMsg(oPatientDiscoveryResponseMessage, oAssertion);
          }

          if (oReturnLogEventRequestType == null)
          {
              addLogError("There was a problem translating the response message to an audit log object");
              return null;
          }
          else
          {
              oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION);
              oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_ENTITY_INTERFACE);
          }
         
          addLogInfo("***************************************************************");
          addLogInfo("Exiting transformEntityPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("***************************************************************");

          return oReturnLogEventRequestType;
    }

    /**
     * this method tranforms a patient discovery response for an outbound Nhin response into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformNhinPRPAIN201306ResponseToAuditMsg(PRPAIN201306UV oResponseMessage, AssertionType oAssertion)
    {
          LogEventRequestType oReturnLogEventRequestType = null;

          addLogInfo("****************************************************************");
          addLogInfo("Entering transformNhinPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("****************************************************************");

          if (oResponseMessage == null)
          {
              addLogError("The Patient Discovery response message was null.");
              return null;
          }

          if (oAssertion == null)
          {
              addLogError("The assertionType object parameter was null.");
              return null;
          }
          else
          {
              oReturnLogEventRequestType = transformPRPAIN201306ResponseToAuditMsg(oResponseMessage, oAssertion);
          }

          if (oReturnLogEventRequestType == null)
          {
              addLogError("There was a problem translating the response message to an audit log object");
              return null;
          }
          else
          {
              oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION);
              oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_NHIN_INTERFACE);
          }

          addLogInfo("***************************************************************");
          addLogInfo("Exiting transformNhinPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("***************************************************************");

          return oReturnLogEventRequestType;
    }

    /**
     * this method tranforms a patient discovery response for an outbound Adapter response into an audit log message.
     * @param message
     * @return
     */
    public LogEventRequestType transformAdapterPRPAIN201306ResponseToAuditMsg(PRPAIN201306UV oResponseMessage, AssertionType oAssertion)
    {
          LogEventRequestType oReturnLogEventRequestType = null;

          addLogInfo("****************************************************************");
          addLogInfo("Entering transformAdapterPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("****************************************************************");

          if (oResponseMessage == null)
          {
              addLogError("The Patient Discovery response message was null.");
              return null;
          }

          if (oAssertion == null)
          {
              addLogError("The assertionType object parameter was null.");
              return null;
          }
          else
          {
              oReturnLogEventRequestType = transformPRPAIN201306ResponseToAuditMsg(oResponseMessage, oAssertion);
          }

          if (oReturnLogEventRequestType == null)
          {
              addLogError("There was a problem translating the response message to an audit log object");
              return null;
          }
          else
          {
              oReturnLogEventRequestType.setDirection(NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION);
              oReturnLogEventRequestType.setInterface(NhincConstants.AUDIT_LOG_ADAPTER_INTERFACE);
          }

          addLogInfo("***************************************************************");
          addLogInfo("Exiting transformAdapterPRPAIN201306ResponseToAuditMsg() method.");
          addLogInfo("***************************************************************");

          return oReturnLogEventRequestType;
    }


    /**
     * Instantiating log4j logger
     * @return
     */
    protected Log createLogger()
    {
        return ((log != null) ? log : LogFactory.getLog(getClass()));
    }

    protected ActiveParticipant getActiveParticipant(UserType oUserInfo) {
        // Create Active Participant Section
        //create a method to call the AuditDataTransformHelper - one expectation
        AuditMessageType.ActiveParticipant participant = AuditDataTransformHelper.createActiveParticipantFromUser(oUserInfo, true);
        return participant;
    }

    protected AuditSourceIdentificationType getAuditSourceIdentificationType(String sCommunityId, String sCommunityName) {
        AuditSourceIdentificationType auditSource = AuditDataTransformHelper.createAuditSourceIdentification(sCommunityId, sCommunityName);
        return auditSource;
    }

    protected CodedValueType getCodedValueTypeFor201305UV() {
        // Create EventIdentification
        CodedValueType eventID = AuditDataTransformHelper
                .createEventId(AuditDataTransformConstants.EVENT_ID_CODE_SYS_NAME_PRQ,
                               AuditDataTransformConstants.EVENT_ID_DISPLAY_NAME_PDREQ,
                               AuditDataTransformConstants.EVENT_ID_CODE_SYS_NAME_PRQ,
                               AuditDataTransformConstants.EVENT_ID_DISPLAY_NAME_PDREQ);
        return eventID;
    }

    protected CodedValueType getCodedValueTypeFor201306UV() {
        //else continue
        // Create EventIdentification
        CodedValueType eventID = new CodedValueType();
        eventID = AuditDataTransformHelper.createEventId(AuditDataTransformConstants.EVENT_ID_CODE_SYS_NAME_PRS, AuditDataTransformConstants.EVENT_ID_DISPLAY_NAME_PDRES, AuditDataTransformConstants.EVENT_ID_CODE_SYS_NAME_PRS, AuditDataTransformConstants.EVENT_ID_DISPLAY_NAME_PDRES);
        return eventID;
    }

    protected String getCompositePatientId(String sCommunityId, String sPatientId) {
        sPatientId = AuditDataTransformHelper.createCompositePatientId(sCommunityId, sPatientId);
        return sPatientId;
    }

    protected EventIdentificationType getEventIdentificationType(CodedValueType eventID) {
        EventIdentificationType oEventIdentificationType =
                AuditDataTransformHelper.createEventIdentification(AuditDataTransformConstants.EVENT_ACTION_CODE_READ,
                                                                   AuditDataTransformConstants.EVENT_OUTCOME_INDICATOR_SUCCESS,
                                                                   eventID);
        return oEventIdentificationType;
    }

    protected ParticipantObjectIdentificationType getParticipantObjectIdentificationType(String sPatientId) {
        /* Assign ParticipationObjectIdentification */
        ParticipantObjectIdentificationType participantObject = AuditDataTransformHelper.createParticipantObjectIdentification(sPatientId);
        return participantObject;
    }

    protected void marshalPatientDiscoveryMessage(ByteArrayOutputStream baOutStrm, Object oPatientDiscoveryMessage) throws RuntimeException {
        // Put the contents of the actual message into the Audit Log Message
        try {
            JAXBContext jc = JAXBContext.newInstance("org.hl7.v3");
            Marshaller marshaller = jc.createMarshaller();
            baOutStrm.reset();
            marshaller.marshal(oPatientDiscoveryMessage, baOutStrm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected II getHL7IdentifiersFromRequest(PRPAIN201305UV oPatientDiscoveryRequestMessage)
    {
        if (oPatientDiscoveryRequestMessage == null)
        {
            addLogError("The request parameter object for the getHL7IdentifiersFromRequest() method is null.");
            return null;
        }
        PRPAIN201305UVQUQIMT021001UV01ControlActProcess oControlActProcess =
                    oPatientDiscoveryRequestMessage.getControlActProcess();
        if (oControlActProcess == null)
        {
            addLogError("The ControlActProcess object was missing from the request");
            return null;
        }

        JAXBElement<PRPAMT201306UVQueryByParameter> oQueryByParameter =
                oControlActProcess.getQueryByParameter();
        if (oQueryByParameter == null)
        {
            addLogError("The QueryByParameter object was missing from the request");
            return null;
        }

        PRPAMT201306UVParameterList oParamList =
                oQueryByParameter.getValue().getParameterList();
        if (oParamList == null)
        {
            addLogError("The ParameterList object was missing from the request");
            return null;
        }

        List<PRPAMT201306UVLivingSubjectId> oLivingSubjectId = oParamList.getLivingSubjectId();
        if (oLivingSubjectId == null)
        {
            addLogError("The LivingSubjectId object was missing from the request");
            return null;
        }

        if ((oLivingSubjectId.get(0) != null) &&
            (oLivingSubjectId.get(0).getValue() != null) &&
            (!oLivingSubjectId.get(0).getValue().isEmpty()) &&
            (oLivingSubjectId.get(0).getValue().get(0) != null))
        {
            return oLivingSubjectId.get(0).getValue().get(0);
        }
        else
        {
            addLogError("Unable to extract the HL7 Identifiers (II) object containing the patient id and community id needed for the audit request message");
            return null;
        }
        
    }

    private void addLogInfo(String message){
        log.info(message);
    }

    private void addLogDebug(String message){
        log.debug(message);
    }

    private void addLogError(String message){
        log.error(message);
    }

    /**
     * This method checks to see if all of the audit log request values are
     * in the patient discovery request.
     *
     * Note: requirements for the LogEventRequestType
     * "Direction", required = true
     * "Interface", required = true
     * "EventIdentification", required = true
     * "ActiveParticipant", required = true
     * "AuditSourceIdentification", required = true
     * "ParticipantObjectIdentification", required = false
     *
     * @param oPatientDiscoveryRequestMessage
     * @return
     */
    private boolean areRequired201305fieldsNull(PRPAIN201305UV oPatientDiscoveryRequestMessage) {
        boolean bReturnVal = false;

        // Create EventIdentification - values are not comming from the request, they are internal to this class

        // Create Active Participant Section - comes from the UserType/UserInfo object which is checked earlier

        /* Assign AuditSourceIdentification */
        II oII = getHL7IdentifiersFromRequest(oPatientDiscoveryRequestMessage);
        if (oII == null)
        {
            return true; //error log messages are created from the method above so no need to duplicate here
        }

        //AuditSourceIdentification - comes from the UserType/UserInfo object which is checked earlier

        return bReturnVal;
    }

}
