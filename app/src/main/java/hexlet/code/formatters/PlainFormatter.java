package hexlet.code.formatters;

import hexlet.code.Difference;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.isNull;

public class PlainFormatter {

    protected static String toPlainFormat(LinkedHashMap<String, List<Difference>> diffs) {
        var result = new StringBuilder();

        diffs.keySet().forEach(key -> {
            var changesForKey = diffs.get(key);

            if (changesForKey.size() == 1) {
                var sign = changesForKey.get(0).sign();
                switch (sign) {
                    case '+' -> {
                        result.append(String.format("Property '%s' was added with value: %s", key,
                                formatDiffValue(changesForKey.get(0).diffValue())));
                        result.append("\n");
                    }
                    case '-' -> {
                        result.append(String.format("Property '%s' was removed", key));
                        result.append("\n");
                    }
                    case '=' -> {
                    }
                    default -> throw new UnsupportedOperationException("Symbol isn't used in final report");
                }
            } else {
                result.append(String.format("Property '%s' was updated. From %s to %s", key,
                        formatDiffValue(changesForKey.get(0).diffValue()),
                        formatDiffValue(changesForKey.get(1).diffValue())));
                result.append("\n");
            }
        });

        return result.toString();
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
