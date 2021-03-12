/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest.manager.internal.properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import bulktest.BulkTestManagerException;
import dev.galasa.framework.spi.IConfigurationPropertyStoreService;

@Component(service=BulkTestPropertiesSingleton.class, immediate=true)
public class BulkTestPropertiesSingleton {
    
    private static BulkTestPropertiesSingleton singletonInstance;

    private static void setInstance(BulkTestPropertiesSingleton instance) {
        singletonInstance = instance;
    }
    
    private IConfigurationPropertyStoreService cps;
    
    @Activate
    public void activate() {
        setInstance(this);
    }
    
    @Deactivate
    public void deacivate() {
        setInstance(null);
    }
    
    public static IConfigurationPropertyStoreService cps() throws BulkTestManagerException {
        if (singletonInstance != null) {
            return singletonInstance.cps;
        }
        
        throw new BulkTestManagerException("Attempt to access manager CPS before it has been initialised");
    }
    
    public static void setCps(IConfigurationPropertyStoreService cps) throws BulkTestManagerException {
        if (singletonInstance != null) {
            singletonInstance.cps = cps;
            return;
        }
        
        throw new BulkTestManagerException("Attempt to set manager CPS before instance created");
    }
}
