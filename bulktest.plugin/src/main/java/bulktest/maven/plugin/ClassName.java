package bulktest.maven.plugin;

public class ClassName {
    
    public String packageName;
    public String className;

    @Override
    public String toString() {
        return this.packageName + "." + className;
    }
}
