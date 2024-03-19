package hexlet.code;

import java.nio.file.Paths;
import java.util.Locale;

public class ExtensionUtil {
    public static String getFileExtension(String fileName) {
        var filePath = Paths.get(fileName).toAbsolutePath().normalize().toString();
        int lastIndexOf = filePath.lastIndexOf(".");
        if (lastIndexOf == -1) {
            throw new IllegalArgumentException("File without extension");
        }
        var extension = filePath.substring(lastIndexOf);
        if (SupportedFileExtension.YAML.getExtensions().contains(extension)) {
            return SupportedFileExtension.YAML.getExtensions().get(0);
        }
        return extension.toLowerCase(Locale.ROOT);
    }
}
