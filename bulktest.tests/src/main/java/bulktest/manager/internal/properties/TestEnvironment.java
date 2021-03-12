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

public class TestEnvironment extends CpsProperties {

    private static final Log logger = LogFactory.getLog(TestEnvironment.class);
    
    private static final String DEFAULT_TEST_ENVIRONMENT = "bulky";

    public static String get() {
            try {
                return getStringWithDefault(BulkTestPropertiesSingleton.cps(), DEFAULT_TEST_ENVIRONMENT, "test", "environment");
            } catch (BulkTestManagerException e) {
                logger.error("problem retrieving the test environment, defaulting to " + DEFAULT_TEST_ENVIRONMENT, e);
                return DEFAULT_TEST_ENVIRONMENT;
            }
    }
}
