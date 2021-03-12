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

public class FailureRate extends CpsProperties {

    private static final Log logger = LogFactory.getLog(FailureRate.class);
    
    private static final int DEFAULT_FAILURE_RATE = 5;

    public static int get() {
            try {
                return getIntWithDefault(BulkTestPropertiesSingleton.cps(), DEFAULT_FAILURE_RATE, "failure", "rate");
            } catch (BulkTestManagerException e) {
                logger.error("problem retrieving the failure rate, defaulting to " + DEFAULT_FAILURE_RATE, e);
                return DEFAULT_FAILURE_RATE;
            }
    }
}
