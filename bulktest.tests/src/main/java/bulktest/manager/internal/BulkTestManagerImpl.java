package bulktest.manager.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;

import bulktest.BulkTestEnvironment;
import bulktest.BulkTestManagerField;
import bulktest.IBulkTestEnvironment;
import dev.galasa.ManagerException;
import dev.galasa.framework.spi.AbstractManager;
import dev.galasa.framework.spi.GenerateAnnotatedField;
import dev.galasa.framework.spi.IFramework;
import dev.galasa.framework.spi.IManager;
import dev.galasa.framework.spi.ResourceUnavailableException;
import dev.galasa.framework.spi.language.GalasaTest;

@Component(service = { IManager.class })
public class BulkTestManagerImpl extends AbstractManager {
    protected static final String NAMESPACE = "bulktest";

    private boolean required;
    
    private IBulkTestEnvironment environment;

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
        generateAnnotatedFields(BulkTestManagerField.class);
    }
    
    @GenerateAnnotatedField(annotation = BulkTestEnvironment.class)
    public IBulkTestEnvironment generateEnvironment(Field field, List<Annotation> annotations) throws ManagerException {
        
        if (this.environment == null) {
            this.environment = new BulkTestEnvironmentImpl();
        }
        
        return this.environment;
    }

}
