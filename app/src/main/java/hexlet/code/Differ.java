package hexlet.code;

import java.util.HashSet;

import static hexlet.code.ExtensionUtil.getFileExtension;
import static hexlet.code.Parser.readFileIntoMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
                if (isNull(fileMap1.get(key)) & (isNull(fileMap2.get(key)))
                        || nonNull(fileMap1.get(key)) && nonNull(fileMap2.get(key))
                        && fileMap1.get(key).equals(fileMap2.get(key))) {
                    var value = getValue(fileMap1.get(key));
                    appendLineChanges(result, '=', key, value);
                } else {
                    var value1 = getValue(fileMap1.get(key));
                    var value2 = getValue(fileMap2.get(key));
                    appendLineChanges(result, '-', key, value1);
                    appendLineChanges(result, '+', key, value2);
                }
            } else if (fileMap1.containsKey(key)) {
                var value = getValue(fileMap1.get(key));
                appendLineChanges(result, '-', key, value);
            } else {
                var value = getValue(fileMap2.get(key));
                appendLineChanges(result, '+', key, value);
            }
        });

        result.append("}\n");
        System.out.println(result);
        return result.toString();
    }

    private static String getValue(Object objectForKey) {
        return isNull(objectForKey) ? "null" : objectForKey.toString();
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
