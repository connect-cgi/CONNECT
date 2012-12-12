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
package gov.hhs.fha.nhinc.direct;

import static gov.hhs.fha.nhinc.direct.DirectUnitTestUtil.getFileAsString;
import static gov.hhs.fha.nhinc.direct.DirectUnitTestUtil.removeSmtpAgentConfig;
import static gov.hhs.fha.nhinc.direct.DirectUnitTestUtil.writeSmtpAgentConfig;
import static org.mockito.Mockito.mock;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.nhindirect.gateway.smtp.SmtpAgent;
import org.nhindirect.gateway.smtp.SmtpAgentFactory;

/**
 * the SMTP/IMAP using gmail.
 */
public class DirectClientGmailTest {
    
    private final Properties props = getMailServerProps();
    
    private static final String TIMEOUT_CONNECTION_MILLIS = Long.toString(TimeUnit.SECONDS.toMillis(15));
    private static final String TIMEOUT_MILLIS = Long.toString(TimeUnit.MINUTES.toMillis(3));
    
    /**
     * Set up keystore for test.
     */
    @BeforeClass
    public static void setUpClass() {
        writeSmtpAgentConfig();
    }
    
    /**
     * Tear down keystore created in setup.
     */
    @AfterClass
    public static void tearDownClass() {
        removeSmtpAgentConfig();
    }  
    

    /**
     * Prove that fetch problem for {@link MimeMessage#getRecipients(javax.mail.Message.RecipientType)} is related to
     * greenmail and not the client code.
     * @throws Exception on error.
     */
    @Test
    @Ignore
    public void testImapsFetchWithGmail() throws Exception {        
        MessageHandler mockHandler = mock(MessageHandler.class);
        DirectMailClient directClient = new DirectMailClient(props, getSmtpAgent());
        directClient.setMessageHandler(mockHandler);
        initiateEmail();
        directClient.handleMessages();
    }

    /**
     * Sets up the properties in order to connect to the green mail test server.
     * 
     * @param smtpPort for smtps
     * @param imapPort for imaps
     * @return Properties instance holding appropriate values for java mail.
     */
    private static Properties getMailServerProps() {

        Properties props = new Properties();

        props.setProperty("direct.mail.user", "xxx");
        props.setProperty("direct.mail.pass", "xxx");
        props.setProperty("direct.max.msgs.in.batch", "5");

        props.setProperty("direct.mail.session.debug", "true");
        props.setProperty("direct.delete.unhandled.msgs", "false");
        
        props.setProperty("mail.smtp.host", "smtp-01.direct.connectopensource.org");
        props.setProperty("mail.smtp.auth", "false");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.starttls.enable", "true");        
        
        props.setProperty("mail.imaps.host", "imap-01.direct.connectopensource.org");
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.connectiontimeout", TIMEOUT_CONNECTION_MILLIS);
        props.setProperty("mail.imaps.timeout", TIMEOUT_MILLIS);

        return props;
    }
    
    private SmtpAgent getSmtpAgent() {
        return SmtpAgentFactory.createAgent(getClass().getClassLoader().getResource("smtp.agent.config.xml"));
    }

    private void initiateEmail() throws Exception {
       
        Session session = MailUtils.getMailSession(props, props.getProperty("direct.mail.user"),
                props.getProperty("direct.mail.pass"));
        MimeMessage originalMsg = new MimeMessage(session,
                IOUtils.toInputStream(getFileAsString("PlainOutgoingMessage.txt")));
        session.setDebug(true);
        session.setDebugOut(System.out);
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(originalMsg, originalMsg.getAllRecipients());
        } finally {
            transport.close();
        }
    }
    

}
