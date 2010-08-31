package gov.hhs.fha.nhinc.docretrieve.passthru.deferred.response.proxy;

import gov.hhs.fha.nhinc.proxy.ComponentProxyObjectFactory;

/**
 *
 * @author Sai Valluripalli
 */
public class PassthruDocRetrieveDeferredRespProxyObjectFactory extends ComponentProxyObjectFactory {

    private static final String CONFIG_FILE_NAME = "DocumentRetrieveDeferredProxyConfig.xml";
    private static final String BEAN_NAME_NHINCPROXY_DOCRETRIEVE_DEFERRED_RESPONSE = "nhincproxydocretrievedeferredresp";

    /**
     * Retrieve an Nhinc proxy Document Retrieve Deferred Response implementation
     * using the IOC framework.
     * This method retrieves the object from the framework that has an
     * identifier of "nhincproxydocretrievedeferredresp".
     * @return NhincProxyDocRetrieveDeferredRespProxy
     */
    public PassthruDocRetrieveDeferredRespProxy getNhincProxyDocRetrieveDeferredRespProxy() {
        return getBean(BEAN_NAME_NHINCPROXY_DOCRETRIEVE_DEFERRED_RESPONSE, PassthruDocRetrieveDeferredRespProxy.class);
    }

    /**
     * 
     * @return String
     */
    protected String getConfigFileName() {
        return CONFIG_FILE_NAME;
    }
}
