package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Formatter {
    public static final String PLAIN_FORMAT = "plain";
    public static final String STYLISH_FORMAT = "stylish";
    public static final String JSON_FORMAT = "json";

    public static String toFormat(String format, LinkedHashMap<String, List<Map<String, Object>>> diffs) {
        switch (format) {
            case STYLISH_FORMAT -> {
                return StylishFormatter.toFormat(diffs);
            }
            case PLAIN_FORMAT -> {
                return PlainFormatter.toFormat(diffs);
            }
            case JSON_FORMAT -> {
                return JsonFormatter.toFormat(diffs);
            }
            default -> throw new UnsupportedOperationException("Format is not supported");
        }
    }
}
