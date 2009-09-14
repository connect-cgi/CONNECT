/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.transform.policy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import oasis.names.tc.xacml._2_0.context.schema.os.SubjectType;

/**
 *
 * @author rayj
 */
public class SubjectHelper {

    private static final String SubjectCategory = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
//    private static final String UserAttributeId = "urn:oasis:names:tc:xacml:1.0:subject:subject-id";
    private static final String UserRoleAttributeId = "urn:gov:hhs:fha:nhinc:user-role-code";
    private static final String PurposeAttributeId = "urn:gov:hhs:fha:nhinc:purpose-for-use";
    private static final String UserHomeCommunityAttributeId = "urn:gov:hhs:fha:nhinc:home-community-id";

    public static SubjectType subjectFactory(HomeCommunityType sendingHomeCommunity, AssertionType assertion) {
        SubjectType subject = new SubjectType();
        subject.setSubjectCategory(SubjectCategory);
        //subject.getAttribute().add(AttributeHelper.attributeFactory(UserAttributeId, Constants.DataTypeString, AssertionHelper.extractUserName(assertion)));
        subject.getAttribute().add(AttributeHelper.attributeFactory(UserHomeCommunityAttributeId, Constants.DataTypeString, determineSendingHomeCommunityId(sendingHomeCommunity, assertion)));
        return subject;
    }
    
    public static SubjectType subjectFactoryReident(HomeCommunityType sendingHomeCommunity, AssertionType assertion) {
        SubjectType subject = new SubjectType();
        subject.setSubjectCategory(SubjectCategory);
        // removed as this causes the user-role-code to show up twice
        //subject.getAttribute().add(AttributeHelper.attributeFactory(UserRoleAttributeId, Constants.DataTypeString, AssertionHelper.extractUserRole(assertion)));
        subject.getAttribute().add(AttributeHelper.attributeFactory(PurposeAttributeId, Constants.DataTypeString, AssertionHelper.extractPurpose(assertion)));
        subject.getAttribute().add(AttributeHelper.attributeFactory(UserHomeCommunityAttributeId, Constants.DataTypeString, determineSendingHomeCommunityId(sendingHomeCommunity, assertion)));
        return subject;
    }

    private static String determineSendingHomeCommunityId(HomeCommunityType sendingHomeCommunity, AssertionType assertion) {
        String homeCommunityId = null;

        if (sendingHomeCommunity != null) {
            homeCommunityId = sendingHomeCommunity.getHomeCommunityId();
        }

        if (NullChecker.isNullish(homeCommunityId)) {
            homeCommunityId = AssertionHelper.extractUserHomeCommunity(assertion);
        }

        return homeCommunityId;
    }
}
