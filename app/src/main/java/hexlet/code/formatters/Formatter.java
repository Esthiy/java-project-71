package hexlet.code.formatters;

import hexlet.code.Difference;

import java.util.LinkedHashMap;
import java.util.List;

import static hexlet.code.formatters.JsonFormatter.toJsonFormat;
import static hexlet.code.formatters.PlainFormatter.toPlainFormat;

public class Formatter {
    public static final String PLAIN_FORMAT = "plain";
    public static final String STYLISH_FORMAT = "stylish";

    public static String toFormat(String format, LinkedHashMap<String, List<Difference>> diffs) {
        switch (format) {
            case STYLISH_FORMAT -> {
                return toJsonFormat(diffs);
            }
            case PLAIN_FORMAT -> {
                return toPlainFormat(diffs);
            }
            default -> throw new UnsupportedOperationException("Format is not supported");
        }
    }
}
