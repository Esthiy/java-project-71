package hexlet.code;

import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class ExtensionUtil {
    public static final List<String> YAML = List.of(".yaml", ".yml");
    public static final String JSON = ".json";

    public static String getFileExtension(String fileName) {
        var filePath = Paths.get(fileName).toAbsolutePath().normalize().toString();
        int lastIndexOf = filePath.lastIndexOf(".");
        if (lastIndexOf == -1) {
            throw new IllegalArgumentException("File without extension");
        }
        var extension = filePath.substring(lastIndexOf);
        if (YAML.contains(extension)) {
            return YAML.get(0);
        }
        return extension.toLowerCase(Locale.ROOT);
    }
}
