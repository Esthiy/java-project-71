package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.ADDED_DIFF;
import static hexlet.code.Differ.DELETED_DIFF;
import static hexlet.code.Differ.UNCHANGED_DIFF;
import static java.util.Objects.isNull;

public class StylishFormatter {

    protected static String toFormat(LinkedHashMap<String, List<Map<String, Object>>> diffs) {
        var result = new StringBuilder("{\n");
        diffs.keySet().forEach(key -> appendLineChanges(result, key, diffs.get(key)));

        result.append("}");
        return result.toString();
    }

    private static void appendLineChanges(StringBuilder result, String keyName, List<Map<String, Object>> diffList) {

        diffList.forEach(diff -> {
            var changeType = diff.keySet().iterator().next();
            var diffValue = diff.get(changeType);

            switch (changeType) {
                case UNCHANGED_DIFF -> result.append("    ");
                case DELETED_DIFF -> result.append("  - ");
                case ADDED_DIFF -> result.append("  + ");
                default -> throw new IllegalArgumentException("Incorrect change type");
            }

            result.append(keyName);
            result.append(": ");
            result.append(isNull(diffValue) ? "null" : diffValue.toString());
            result.append("\n");
        });
    }
}
