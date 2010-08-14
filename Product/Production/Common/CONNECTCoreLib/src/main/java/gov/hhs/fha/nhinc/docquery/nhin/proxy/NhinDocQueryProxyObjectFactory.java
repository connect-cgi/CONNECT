/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.docquery.nhin.proxy;

import gov.hhs.fha.nhinc.proxy.ComponentProxyObjectFactory;

/**
 * @author Jon Hoppesch
 */
public class NhinDocQueryProxyObjectFactory extends ComponentProxyObjectFactory {

private static final String CONFIG_FILE_NAME = "NhinDocQueryProxyConfig.xml";
    private static final String BEAN_NAME = "nhindocquery";

    protected String getConfigFileName()
    {
        return CONFIG_FILE_NAME;
    }

    public NhinDocQueryProxy getNhinDocQueryProxy()
    {
        return getBean(BEAN_NAME, NhinDocQueryProxy.class);
   }
}
