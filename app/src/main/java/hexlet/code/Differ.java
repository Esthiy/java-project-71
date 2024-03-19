package hexlet.code;

import java.util.HashSet;

import static hexlet.code.ExtensionUtil.getFileExtension;
import static hexlet.code.Parser.readFileIntoMap;

public class Differ {


    public static String generate(String filePath1, String filePath2) throws Exception {
        var extension1 = getFileExtension(filePath1);
        var extension2 = getFileExtension(filePath2);

        if (!extension1.equalsIgnoreCase(extension2)) {
            throw new IllegalArgumentException("Files extensions are not the same");
        }

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
        System.out.println(result);
        return result.toString();
    }

    private static void appendLineChanges(StringBuilder stringBuilder, char sign, String lineKey, Object lineContent) {
        switch (sign) {
            case '+' -> stringBuilder.append("  + ");
            case '-' -> stringBuilder.append("  - ");
            case '=' -> stringBuilder.append("    ");
            default -> throw new UnsupportedOperationException("Symbol isn't used in final report");
        }
        stringBuilder.append(lineKey);
        stringBuilder.append(": ");
        stringBuilder.append(lineContent);
        stringBuilder.append("\n");
    }
}
