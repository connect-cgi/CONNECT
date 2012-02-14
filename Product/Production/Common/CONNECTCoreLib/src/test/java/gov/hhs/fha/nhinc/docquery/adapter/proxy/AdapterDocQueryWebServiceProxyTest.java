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
package gov.hhs.fha.nhinc.docquery.adapter.proxy;

import gov.hhs.fha.nhinc.adapterdocquerysecured.AdapterDocQuerySecuredPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import javax.xml.ws.Service;
import org.apache.commons.logging.Log;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * 
 * @author Neil Webb
 */
@RunWith(JMock.class)
public class AdapterDocQueryWebServiceProxyTest {
    Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    final Log mockLog = context.mock(Log.class);
    final Service mockService = context.mock(Service.class);
    final AdapterDocQuerySecuredPortType mockPort = context.mock(AdapterDocQuerySecuredPortType.class);

    @Test
    public void testCreateLogger() {
        try {
            AdapterDocQueryProxyWebServiceSecuredImpl sut = new AdapterDocQueryProxyWebServiceSecuredImpl() {
                @Override
                protected Log createLogger() {
                    return mockLog;
                }

            };
            Log log = sut.createLogger();
            assertNotNull("Log was null", log);
        } catch (Throwable t) {
            System.out.println("Error running testCreateLogger test: " + t.getMessage());
            t.printStackTrace();
            fail("Error running testCreateLogger test: " + t.getMessage());
        }
    }

    @Test
    public void testGetService() {
        try {
            AdapterDocQueryProxyWebServiceSecuredImpl sut = new AdapterDocQueryProxyWebServiceSecuredImpl() {
                @Override
                protected Log createLogger() {
                    return mockLog;
                }

                @Override
                protected Service getService() {
                    return mockService;
                }
            };
            Service service = sut.getService();
            assertNotNull("Service was null", service);
        } catch (Throwable t) {
            System.out.println("Error running testGetService test: " + t.getMessage());
            t.printStackTrace();
            fail("Error running testGetService test: " + t.getMessage());
        }
    }

    @Test
    public void testGetPortNullService() {
        try {
            AdapterDocQueryProxyWebServiceSecuredImpl sut = new AdapterDocQueryProxyWebServiceSecuredImpl() {
                @Override
                protected Log createLogger() {
                    return mockLog;
                }

                @Override
                protected Service getService() {
                    return null;
                }
            };
            context.checking(new Expectations() {
                {
                    allowing(mockLog).debug(with(any(String.class)));
                    oneOf(mockLog).error("Unable to obtain serivce - no port created.");
                }
            });
            String url = "url";
            AdapterDocQuerySecuredPortType port = sut.getPort(url, NhincConstants.DOC_QUERY_ACTION,
                    "urn:gov:hhs:fha:nhinc:adapterdocquerysecured:AdapterDocQueryRequestSecuredMessage",
                    new AssertionType());
            assertNull("Port was not null", port);
        } catch (Throwable t) {
            System.out.println("Error running testGetPortNullService test: " + t.getMessage());
            t.printStackTrace();
            fail("Error running testGetPortNullService test: " + t.getMessage());
        }
    }

}