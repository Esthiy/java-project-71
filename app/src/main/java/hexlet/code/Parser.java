package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static hexlet.code.ExtensionUtil.getFileExtension;

public class Parser {

    public static Map<String, Object> readFileIntoMap(String filePath) throws Exception {
        // Чтение файла:
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File '" + path + "' does not exist");
        }

        var extension = getFileExtension(path.toString());

        if (SupportedFileExtension.JSON.getExtensions().contains(extension)) {
            return new ObjectMapper().readValue(Files.readString(path), new TypeReference<>() {
            });
        } else if (SupportedFileExtension.YAML.getExtensions().contains(extension)) {
            return new YAMLMapper().readValue(Files.readString(path), new TypeReference<>() {
            });
        } else {
            throw new IllegalArgumentException("File is not json or xml");
        }
    }
}
