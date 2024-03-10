package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;

public class Differ {


    public static String generate(String filePath1, String filePath2) throws Exception {
        var fileMap1 = readFileIntoMap(filePath1);
        var fileMap2 = readFileIntoMap(filePath2);

        var result = new StringBuilder("{\n");
        var keys = new HashSet<>(fileMap1.keySet());
        keys.addAll(fileMap2.keySet());

        keys.stream().sorted().forEach(key -> {
            if (fileMap1.containsKey(key) && fileMap2.containsKey(key)) {
                if (fileMap1.get(key).equals(fileMap2.get(key))) {
                    appendLineChanges(result, '=', key, fileMap1.get(key));
                } else {
                    appendLineChanges(result, '-', key, fileMap1.get(key));
                    appendLineChanges(result, '+', key, fileMap2.get(key));
                }
            } else if (fileMap1.containsKey(key)) {
                appendLineChanges(result, '-', key, fileMap1.get(key));
            } else {
                appendLineChanges(result, '+', key, fileMap2.get(key));
            }
        });

        result.append("}\n");
        return result.toString();
    }

    private static Map<String, Object> readFileIntoMap(String filePath) throws Exception {
        // Чтение файла:
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File '" + path + "' does not exist");
        }

        return new ObjectMapper().readValue(Files.readString(path), new TypeReference<>() {
        });
    }

    private static void appendLineChanges(StringBuilder stringBuilder, char sign, String lineKey, Object lineContent) {
        switch (sign) {
            case '+' -> stringBuilder.append("  + ");
            case '-' -> stringBuilder.append("  - ");
            case '=' -> stringBuilder.append("    ");
            default -> throw new UnsupportedOperationException("Указан неверный символ");
        }
        stringBuilder.append(lineKey);
        stringBuilder.append(": ");
        stringBuilder.append(lineContent);
        stringBuilder.append("\n");
    }
}
