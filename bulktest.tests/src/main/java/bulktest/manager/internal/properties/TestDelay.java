/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest.manager.internal.properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bulktest.BulkTestManagerException;
import dev.galasa.framework.spi.cps.CpsProperties;

public class TestDelay extends CpsProperties {

    private static final Log logger = LogFactory.getLog(TestDelay.class);
    
    private static final int DEFAULT_TEST_DELAY = 25;

    public static int get() {
            try {
                return getIntWithDefault(BulkTestPropertiesSingleton.cps(), DEFAULT_TEST_DELAY, "test", "delay");
            } catch (BulkTestManagerException e) {
                logger.error("problem retrieving the test delay, defaulting to " + DEFAULT_TEST_DELAY, e);
                return DEFAULT_TEST_DELAY;
            }
    }
}
