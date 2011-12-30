/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.docretrieve.entity;

import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.orchestration.CONNECTEntityOrchestrator;
import gov.hhs.fha.nhinc.orchestration.EntityOrchestratable;
import gov.hhs.fha.nhinc.orchestration.NhinAggregator;
import gov.hhs.fha.nhinc.orchestration.Orchestratable;
import gov.hhs.fha.nhinc.orchestration.PolicyTransformer;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType.DocumentRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mweaver
 */
public class EntityDocRetrieveOrchestratorImpl extends CONNECTEntityOrchestrator {

    private static final Log logger = LogFactory.getLog(EntityDocRetrieveOrchestratorImpl.class);

    @Override
    public void process(Orchestratable message) {
        getLogger().debug("Entering EntityDocRetrieveOrchestratorImpl for " + message.getServiceName());
        if (message != null) {
            if (message instanceof EntityDocRetrieveOrchestratable) {
                // audit
                getLogger().debug("Calling audit for " + message.getServiceName());
                auditRequest(message);

                if (message.isEnabled()) {
                    getLogger().debug(message.getServiceName() + " service is enabled. Procesing message...");

                    EntityDocRetrieveOrchestratable EntityDROrchMessage = (EntityDocRetrieveOrchestratable)message;
                    for (DocumentRequest docRequest : EntityDROrchMessage.getRequest().getDocumentRequest()) {
                        EntityOrchestratable impl = new EntityDocRetrieveOrchestratable(message.getPolicyTransformer(), message.getAuditTransformer(), EntityDROrchMessage.getNhinDelegate(), EntityDROrchMessage.getAggregator());
                        RetrieveDocumentSetRequestType rdRequest = new RetrieveDocumentSetRequestType();
                        rdRequest.getDocumentRequest().add(docRequest);
                        ((EntityDocRetrieveOrchestratable)impl).setRequest(rdRequest);
                        ((EntityDocRetrieveOrchestratable)impl).setAssertion(message.getAssertion());
                        ((EntityDocRetrieveOrchestratable)impl).setTarget(buildHomeCommunity(docRequest.getHomeCommunityId()));
                        
                        // policy check
                        if (isPolicyOk(impl, PolicyTransformer.Direction.OUTBOUND)) {
                            // if true, send to Nhin
                            impl = delegateToNhin(impl);
                        } else {
                            getLogger().debug("Policy Check failed for " + message.getServiceName());
                        }

                        // TODO: how do we aggregate!?!?
                        NhinAggregator agg = EntityDROrchMessage.getAggregator();
                        agg.aggregate((EntityOrchestratable)message, impl);
                    }
                }
                // audit again
                getLogger().debug("Calling audit response for " + message.getServiceName());
                auditResponse(message);
            }
        }
    }

    private NhinTargetSystemType buildHomeCommunity(String homeCommunityId) {
        NhinTargetSystemType nhinTargetSystem = new NhinTargetSystemType();
        HomeCommunityType homeCommunity = new HomeCommunityType();
        homeCommunity.setHomeCommunityId(homeCommunityId);
        nhinTargetSystem.setHomeCommunity(homeCommunity);
        return nhinTargetSystem;
    }

    @Override
    public Log getLogger() {
        return logger;
    }
}
