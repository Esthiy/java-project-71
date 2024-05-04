package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static hexlet.code.ExtensionUtil.JSON_EXTENSION;
import static hexlet.code.ExtensionUtil.YAML_EXTENSION_LIST;
import static hexlet.code.ExtensionUtil.getFileExtension;

public class Parser {

    public static Map<String, Object> readFileIntoMap(String filePath) throws Exception {
        // Чтение файла:
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File '" + path + "' does not exist");
        }

        var extension = getFileExtension(path.toString());

        if (JSON_EXTENSION.contains(extension)) {
            return new ObjectMapper().readValue(Files.readString(path), new TypeReference<>() {
            });
        } else if (YAML_EXTENSION_LIST.contains(extension)) {
            return new YAMLMapper().readValue(Files.readString(path), new TypeReference<>() {
            });
        } else {
            throw new IllegalArgumentException("File is not json or xml");
        }
    }
}
