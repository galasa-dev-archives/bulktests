package bulktest.maven.plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ClassNameManagement {

    private static final ArrayList<String> CITIES = new ArrayList<>();
    static {
        CITIES.add("bristol");
        CITIES.add("london");
        CITIES.add("southampton");
        CITIES.add("cambridge");                     
        CITIES.add("chester");                     
        CITIES.add("derby");                     
        CITIES.add("dundee");                     
        CITIES.add("edinburgh");                     
        CITIES.add("cardiff");                     
        CITIES.add("swansea");                     
        CITIES.add("gloucester");                     
        CITIES.add("manchester");                     
        CITIES.add("newport");                     
        CITIES.add("norwich");                     
        CITIES.add("york");                     
        CITIES.add("oxford");                     
        CITIES.add("winchester");                     
        CITIES.add("westminster");                     
    };

    private static final ArrayList<String> BIRDS = new ArrayList<>();
    static {
        BIRDS.add("Robin");
        BIRDS.add("Blackbird");
        BIRDS.add("GoldenEagle");
        BIRDS.add("Osprey");                     
        BIRDS.add("Owl");                     
        BIRDS.add("Osprey");                     
        BIRDS.add("Buzzard");                     
        BIRDS.add("Goose");                     
        BIRDS.add("Crow");                     
        BIRDS.add("Chaffinch");                     
    };
    
    private static final ArrayList<String> TREES = new ArrayList<>();
    static {
        TREES.add("oak");
        TREES.add("chestnut");
        TREES.add("ash");
        TREES.add("beech");                     
        TREES.add("cedar");                     
        TREES.add("spruce");                     
    };
    

    private final HashSet<String> usedClassNames = new HashSet<>();

    private final Random random = new Random();

    public ClassName generateClassName() throws Exception {

        for(int i = 0; i < 10000; i++) {
            ArrayList<String> availableQualifiers = new ArrayList<>(CITIES);

            StringBuilder classNameBuilder = new StringBuilder("bulktest");

            for(int j = 0; j < 3; j++) {
                String qualifier = availableQualifiers.remove(random.nextInt(availableQualifiers.size()));

                classNameBuilder.append(".");
                classNameBuilder.append(qualifier);
            }

            String packageName = classNameBuilder.toString();
            String className = BIRDS.get(random.nextInt(BIRDS.size()));

            classNameBuilder.append(".");
            classNameBuilder.append(className);

            String fullClassName = classNameBuilder.toString();

            if (!this.usedClassNames.contains(fullClassName)) {
                this.usedClassNames.add(fullClassName);

                ClassName cn = new ClassName();
                cn.packageName = packageName;
                cn.className   = className;
                return cn;
            }
        }

        throw new Exception("Unable to find free class name");
    }

    public String[] generateTags() {
        ArrayList<String> tags = new ArrayList<>();
        
        int numberOfTags = this.random.nextInt(3); // zero is valid
        ArrayList<String> availableTags = new ArrayList<>(TREES);
        
        for(int i = 0; i < numberOfTags; i++) {
            tags.add(availableTags.remove(this.random.nextInt(availableTags.size())));
        }
        
        return tags.toArray(new String[tags.size()]);
    }

}
