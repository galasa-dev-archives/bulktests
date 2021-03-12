/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest.manager.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Component;

import bulktest.BulkTestEnvironment;
import bulktest.BulkTestManagerException;
import bulktest.BulkTestManagerField;
import bulktest.IBulkTestEnvironment;
import bulktest.manager.internal.properties.BulkTestPropertiesSingleton;
import bulktest.manager.internal.properties.TestEnvironment;
import dev.galasa.ManagerException;
import dev.galasa.framework.spi.AbstractManager;
import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.GenerateAnnotatedField;
import dev.galasa.framework.spi.IFramework;
import dev.galasa.framework.spi.ILoggingManager;
import dev.galasa.framework.spi.IManager;
import dev.galasa.framework.spi.ResourceUnavailableException;
import dev.galasa.framework.spi.language.GalasaTest;

@Component(service = { IManager.class })
public class BulkTestManagerImpl extends AbstractManager implements ILoggingManager {
    protected static final String NAMESPACE = "bulktest";
    
    private static final Log logger = LogFactory.getLog(BulkTestManagerImpl.class);

    private boolean required;
    
    private IBulkTestEnvironment environment;
    
    private String testEnvironment;

    @Override
    public void initialise(@NotNull IFramework framework, @NotNull List<IManager> allManagers,
            @NotNull List<IManager> activeManagers, @NotNull GalasaTest galasaTest) throws ManagerException {
        super.initialise(framework, allManagers, activeManagers, galasaTest);

        if(galasaTest.isJava()) {
            if (findAnnotatedFields(BulkTestManagerField.class).isEmpty() && !required) {
                return;
            }
        } else {
            return; // Dont support anything other than Java at the moment
        }

        youAreRequired(allManagers, activeManagers);
        
        try {
            BulkTestPropertiesSingleton.setCps(framework.getConfigurationPropertyService(NAMESPACE));
        } catch (ConfigurationPropertyStoreException e) {
            throw new BulkTestManagerException("Unable to request framework services", e);
        }
        
    }
    
    @Override
    public void youAreRequired(@NotNull List<IManager> allManagers, @NotNull List<IManager> activeManagers)
            throws ManagerException {
        
        if (activeManagers.contains(this)) {
            return;
        }

        this.required = true;
        activeManagers.add(this);

    }
    
    @Override
    public void provisionGenerate() throws ManagerException, ResourceUnavailableException {
        this.testEnvironment = TestEnvironment.get();
        
        logger.info("Test Environment has been set to '" + this.testEnvironment + "'");
        
        generateAnnotatedFields(BulkTestManagerField.class);
    }
    
    @GenerateAnnotatedField(annotation = BulkTestEnvironment.class)
    public IBulkTestEnvironment generateEnvironment(Field field, List<Annotation> annotations) throws ManagerException {
        
        if (this.environment == null) {
            this.environment = new BulkTestEnvironmentImpl();
        }
        
        return this.environment;
    }

    @Override
    public String getTestTooling() {
        return null;
    }

    @Override
    public String getTestType() {
        return null;
    }

    @Override
    public String getTestingEnvironment() {
        return this.testEnvironment;
    }

    @Override
    public String getProductRelease() {
        return null;
    }

    @Override
    public String getBuildLevel() {
        return null;
    }

    @Override
    public String getCustomBuild() {
        return null;
    }

    @Override
    public List<String> getTestingAreas() {
        return null;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}
