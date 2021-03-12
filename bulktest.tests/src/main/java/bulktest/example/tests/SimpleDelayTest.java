/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest.example.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bulktest.BulkTestEnvironment;
import bulktest.IBulkTestEnvironment;
import dev.galasa.Tags;
import dev.galasa.Test;

@Test
@Tags({"hello"})
public class SimpleDelayTest {
    
    private static final Log logger = LogFactory.getLog(SimpleDelayTest.class);
    
    @BulkTestEnvironment
    public IBulkTestEnvironment environment;
    
    @Test
    public void test1() throws Exception {
        
        long delay = this.environment.getDelay();
        
        logger.info("Delaying for " + delay + " seconds");
        
        Thread.sleep(environment.getDelay() * 1000);
        
        if (environment.shouldFail()) {
            throw new Exception("I have been requested to fail");
        } else {
            logger.info("I have been requested to pass");
        }
    }

}
