/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest.manager.internal;

import java.util.Random;

import bulktest.IBulkTestEnvironment;
import bulktest.manager.internal.properties.FailureRate;
import bulktest.manager.internal.properties.TestDelay;

public class BulkTestEnvironmentImpl implements IBulkTestEnvironment {
    
    private final Random random = new Random();

    @Override
    public long getDelay() {
        return 5 + random.nextInt(TestDelay.get());
    }

    @Override
    public boolean shouldFail() {
        int perc = random.nextInt(100);
        if (perc < FailureRate.get()) {
            return true;
        }
        return false;
    }

}
