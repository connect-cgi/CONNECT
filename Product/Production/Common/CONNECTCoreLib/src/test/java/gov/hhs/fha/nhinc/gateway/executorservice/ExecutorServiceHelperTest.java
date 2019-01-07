/*
 * Copyright (c) 2009-2019, United States Government, as represented by the Secretary of Health and Human Services.
 *  All rights reserved.
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
package gov.hhs.fha.nhinc.gateway.executorservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ExecutorServiceHelperTest {

    private ExecutorServiceHelper instance;

    @Before
    public void setup() {
        instance = ExecutorServiceHelper.getInstance();
    }

    @Test
    public void formattedExceptionIncludesMessage() {
        Exception e = mock(Exception.class);
        when(e.getMessage()).thenReturn("message");
        String result = ExecutorServiceHelper.getFormattedExceptionInfo(e, null, null);
        assertTrue(result.contains("message"));
    }

    /**
     * Test of getInstance method, of class ExecutorServiceHelper.
     */
    @Test
    public void testGetInstance() {
        ExecutorServiceHelper result = ExecutorServiceHelper.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getExecutorPoolSize method, of class ExecutorServiceHelper.
     */
    @Test
    public void testGetExecutorPoolSize() {
        // default Pool Size
        int expResult = 100;
        int result = instance.getExecutorPoolSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLargeJobExecutorPoolSize method, of class ExecutorServiceHelper.
     */
    @Test
    public void testGetLargeJobExecutorPoolSize() {
        // default value
        int expResult = 200;
        int result = instance.getLargeJobExecutorPoolSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLargeJobPercentage method, of class ExecutorServiceHelper.
     */
    @Test
    public void testGetLargeJobPercentage() {
        double expResult = .75;
        double result = instance.getLargeJobPercentage();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getTimeoutValues method, of class ExecutorServiceHelper.
     */
    @Test
    public void testGetTimeoutValues() {
        // set the default values
        Map expResult = new HashMap();
        Map result = instance.getTimeoutValues();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkExecutorTaskIsLarge method, of class ExecutorServiceHelper.
     */
    @Test
    public void testCheckExecutorTaskIsLarge() {
        int targetListCount = 100;
        boolean expResult = true;
        boolean result = instance.checkExecutorTaskIsLarge(targetListCount);
        assertEquals(expResult, result);
        targetListCount = 60;
        expResult = false;
        result = instance.checkExecutorTaskIsLarge(targetListCount);
        assertEquals(expResult, result);
    }

    /**
     * Test of outputCompleteException method, of class ExecutorServiceHelper.
     */
    @Test
    public void testOutputCompleteException() {
        Exception ex = new Exception("Test Error", new Throwable("Detailed Error Message"));
        ExecutorServiceHelper.outputCompleteException(ex);
    }
}
