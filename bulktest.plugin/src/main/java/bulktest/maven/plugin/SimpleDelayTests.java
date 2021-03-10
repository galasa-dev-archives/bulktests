package bulktest.maven.plugin;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleDelayTests {

    private final ClassNameManagement classNameManagement;
    private final Path sourceDirectory;

    public SimpleDelayTests(ClassNameManagement classNameManagement, File sourceDirectory) {
        this.classNameManagement = classNameManagement;
        this.sourceDirectory     = Paths.get(sourceDirectory.toURI());
    }


    public void generateClass() throws Exception {

        ClassName cn = this.classNameManagement.generateClassName();
        String[] tags = this.classNameManagement.generateTags();

        String simpleClass = "package " + cn.packageName + ";\n"
                + "\n"
                + "import org.apache.commons.logging.Log;\n"
                + "import org.apache.commons.logging.LogFactory;\n"
                + "\n"
                + "import bulktest.BulkTestEnvironment;\n"
                + "import bulktest.IBulkTestEnvironment;\n";

        if (tags.length > 0) {
            simpleClass = simpleClass + "import dev.galasa.Tags;\n";
        }                

        simpleClass = simpleClass 
                + "import dev.galasa.Test;\n"
                + "\n"
                + "@Test\n";

        if (tags.length > 0) {
            String tagAnnotation = "@Tags({";
            for(int i = 0; i < tags.length; i++) {
                if (i > 0) {
                    tagAnnotation = tagAnnotation + ",";
                }
                tagAnnotation = tagAnnotation + "\"" + tags[i] + "\"";
            }
            tagAnnotation = tagAnnotation + "})\n";
            simpleClass = simpleClass + tagAnnotation;
        }

        simpleClass = simpleClass 
                + "public class " + cn.className + " {\n"
                + "\n"
                + "    private static final Log logger = LogFactory.getLog(" + cn.className + ".class);\n"
                + "\n" 
                + "    @BulkTestEnvironment\n" 
                + "    public IBulkTestEnvironment environment;\n" 
                + "\n"
                + "    @Test\n"
                + "    public void test1() throws Exception {\n" 
                + "        long delay = this.environment.getDelay();\n"
                + "\n"
                + "        logger.info(\"Delaying for \" + delay + \" seconds\");\n"
                + "\n"
                + "        Thread.sleep(environment.getDelay() * 1000);\n"
                + "\n"
                + "        if (environment.shouldFail()) {\n"
                + "            throw new Exception(\"I have been requested to fail\");\n"
                + "        } else {\n" 
                + "            logger.info(\"I have been requested to pass\");\n"
                + "        }\n"
                + "    }\n"
                + "}\n";

        Path packageFolder = sourceDirectory.resolve(cn.packageName.replace(".", sourceDirectory.getFileSystem().getSeparator()));
        Files.createDirectories(packageFolder);

        Path classFile = packageFolder.resolve(cn.className + ".java");

        Files.write(classFile, simpleClass.getBytes(StandardCharsets.UTF_8));

        return;
    }

}
