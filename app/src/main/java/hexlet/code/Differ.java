package hexlet.code;

import java.util.HashSet;
import java.util.Map;

public class Differ {

    public static String generate(Map<String, Object> fileMap1, Map<String, Object> fileMap2) {
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

        result.append("}");
        return result.toString();
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
