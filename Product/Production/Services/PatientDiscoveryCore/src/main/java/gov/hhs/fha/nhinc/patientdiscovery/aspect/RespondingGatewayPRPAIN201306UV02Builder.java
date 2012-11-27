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
package gov.hhs.fha.nhinc.patientdiscovery.aspect;

import gov.hhs.fha.nhinc.event.DelegatingEventDescriptionBuilder;

import org.hl7.v3.RespondingGatewayPRPAIN201306UV02RequestType;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02SecuredRequestType;

/**
 * Handles responding gateway duty. What's a bit strange here is that the gateway accepts the wrapped PRPAIN201306UV02
 * object as an argument. The delegate builder expect that object as a return value. So while this is a delegating
 * builder, it is <em>not</em> an argument transformer.
 */
public class RespondingGatewayPRPAIN201306UV02Builder extends DelegatingEventDescriptionBuilder {

    public RespondingGatewayPRPAIN201306UV02Builder() {
        super.setDelegate(new PRPAIN201306UV02EventDescriptionBuilder());
    }

    @Override
    public void setArguments(Object... arguments) {
        if (arguments[0] instanceof RespondingGatewayPRPAIN201306UV02SecuredRequestType) {
            RespondingGatewayPRPAIN201306UV02SecuredRequestType request = (RespondingGatewayPRPAIN201306UV02SecuredRequestType) arguments[0];
            getDelegate().setReturnValue(request.getPRPAIN201306UV02());
        } else if (arguments[0] instanceof RespondingGatewayPRPAIN201306UV02RequestType) {
            RespondingGatewayPRPAIN201306UV02RequestType request = (RespondingGatewayPRPAIN201306UV02RequestType) arguments[0];
            getDelegate().setReturnValue(request.getPRPAIN201306UV02());
        } else {
            throw new IllegalArgumentException("Did not expect arguments[0] to be of type: " + arguments[0].getClass());
        }
    }

    @Override
    public void setReturnValue(Object returnValue) {
        // the PRPAIN201306UV02 is an argument, not a return value.
    }

    public void setDelegate(PRPAIN201306UV02EventDescriptionBuilder delegate) {
        super.setDelegate(delegate);
    }
}
