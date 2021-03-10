package bulktest.manager.internal;

import java.util.Random;

import bulktest.IBulkTestEnvironment;

public class BulkTestEnvironmentImpl implements IBulkTestEnvironment {
    
    private final Random random = new Random();

    @Override
    public long getDelay() {
        return 5 + random.nextInt(20);
    }

    @Override
    public boolean shouldFail() {
        int perc = random.nextInt(100);
        if (perc < 5) {
            return true;
        }
        return false;
    }
    
    

}
