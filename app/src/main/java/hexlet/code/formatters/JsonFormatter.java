package hexlet.code.formatters;

import hexlet.code.Difference;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.isNull;

public class JsonFormatter {

    protected static String toJsonFormat(LinkedHashMap<String, List<Difference>> diffs) {
        var result = new StringBuilder("{\n");

        diffs.keySet().forEach(key ->
                diffs.get(key).forEach(it -> appendLineChanges(result, key, it.sign(), it.diffValue())));

        result.append("}\n");
        return result.toString();
    }

    private static void appendLineChanges(StringBuilder stringBuilder, String keyName, char sign, Object diffValue) {
        switch (sign) {
            case '+' -> stringBuilder.append("  + ");
            case '-' -> stringBuilder.append("  - ");
            case '=' -> stringBuilder.append("    ");
            default -> throw new UnsupportedOperationException("Symbol isn't used in final report");
        }
        stringBuilder.append(keyName);
        stringBuilder.append(": ");
        stringBuilder.append(isNull(diffValue) ? "null" : diffValue.toString());
        stringBuilder.append("\n");
    }
}
