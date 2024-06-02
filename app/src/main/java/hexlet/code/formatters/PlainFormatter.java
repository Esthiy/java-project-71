package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.Differ.ADDED_DIFF;
import static hexlet.code.Differ.DELETED_DIFF;
import static java.util.Objects.isNull;

public class PlainFormatter {

    protected static String toFormat(LinkedHashMap<String, List<Map<String, Object>>> diffs) {
        var result = new StringBuilder();

        diffs.keySet().forEach(key -> {
            var changesForKey = diffs.get(key);

            if (changesForKey.size() == 1) {
                var changeType = changesForKey.get(0).keySet().iterator().next();
                if (changeType.equals(ADDED_DIFF)) {
                    result.append(String.format("Property '%s' was added with value: %s", key,
                            formatDiffValue(changesForKey.get(0).get(ADDED_DIFF))));
                    result.append("\n");
                }
                if (changeType.equals(DELETED_DIFF)) {
                    result.append(String.format("Property '%s' was removed", key));
                    result.append("\n");
                }
            } else {
                result.append(String.format("Property '%s' was updated. From %s to %s", key,
                        formatDiffValue(changesForKey.get(0).get(DELETED_DIFF)),
                        formatDiffValue(changesForKey.get(1).get(ADDED_DIFF))));
                result.append("\n");
            }
        });

        return result.toString().trim();
    }

    private static String formatDiffValue(Object diffValue) {
        if (isNull(diffValue)) {
            return "null";
        }

        if (diffValue instanceof Integer || diffValue instanceof Boolean) {
            return diffValue.toString();
        }

        if (diffValue instanceof String) {
            return String.format("'%s'", diffValue);
        }

        return "[complex value]";
    }
}
