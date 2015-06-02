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
package gov.hhs.fha.nhinc.adaptermpimanager.HL7Parsers;

import org.apache.log4j.Logger;
import org.hl7.v3.II;
import org.hl7.v3.PRPAIN201301UV02;

/**
 *
 * @author rayj
 */
public class HL7Parser {
    private static final Logger LOG = Logger.getLogger(HL7Parser.class);
    public static final String SSN_OID = "2.16.840.1.113883.4.1";

    public static void PrintMessageIdFromMessage(Object message) {
        LOG.debug("Begin HL7Parser.PrintMessageIdFromMessage(Object)");
        if (message != null) {
            if (message instanceof PRPAIN201301UV02) {
                HL7Parser201301.PrintMessageIdFromMessage((PRPAIN201301UV02) message);
            }

        }
        LOG.debug("End HL7Parser.PrintMessageIdFromMessage(Object)");
    }

    public static void PrintId(II id, String idname) {
        if (idname == null) {
            idname = "";
        }
        if (!(id == null)) {
            LOG.info(idname + ".id.root=" + id.getRoot() + ";");
            LOG.info(idname + ".id.extension=" + id.getExtension() + ";");
        } else {
            LOG.info("id for " + idname + " is null");
        }
    }

    public static void PrintId(java.util.List<II> ids, String idname) {
        for (II id : ids) {
            PrintId(id, idname);
        }
    }

}