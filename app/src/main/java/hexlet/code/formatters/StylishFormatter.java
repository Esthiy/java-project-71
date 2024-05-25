package hexlet.code.formatters;

import hexlet.code.Difference;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.isNull;

public class StylishFormatter {

    protected static String toFormat(LinkedHashMap<String, List<Difference>> diffs) {
        var result = new StringBuilder("{\n");

        diffs.keySet().forEach(key ->
                diffs.get(key).forEach(it -> appendLineChanges(result, key, it.isLineAdded(), it.diffValue())));

        result.append("}\n");
        return result.toString();
    }

    private static void appendLineChanges(StringBuilder stringBuilder, String keyName,
                                          Boolean isLineAdded, Object diffValue) {
        if (isNull(isLineAdded)) {
            stringBuilder.append("    ");
        } else if (isLineAdded) {
            stringBuilder.append("  + ");
        } else {
            stringBuilder.append("  - ");
        }

        stringBuilder.append(keyName);
        stringBuilder.append(": ");
        stringBuilder.append(isNull(diffValue) ? "null" : diffValue.toString());
        stringBuilder.append("\n");
    }
}
