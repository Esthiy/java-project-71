package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.ExtensionUtil.getFileExtension;
import static hexlet.code.Parser.readFileIntoMap;
import static hexlet.code.formatters.Formatter.STYLISH_FORMAT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Differ {
    public static final String UNCHANGED_DIFF = "UNCHANGED";
    public static final String DELETED_DIFF = "DELETED";
    public static final String ADDED_DIFF = "ADDED";

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, STYLISH_FORMAT);
    }

    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        // check extension
        var firstFileExtension = getFileExtension(firstFilePath);
        var secondFileExtension = getFileExtension(secondFilePath);

        if (!firstFileExtension.equalsIgnoreCase(secondFileExtension)) {
            throw new IllegalArgumentException("Files extensions are not the same");
        }

        // read files
        var firstFileMap = readFileIntoMap(firstFilePath);
        var secondFileMap = readFileIntoMap(secondFilePath);

        // generate diff
        var diffsMap = generateDiffMap(firstFileMap, secondFileMap);

        return Formatter.toFormat(format, diffsMap);
    }

    private static LinkedHashMap<String, List<Map<String, Object>>> generateDiffMap(Map<String, Object> firstFileMap,
                                                                                    Map<String, Object> secondFileMap) {
        var diffsMap = new LinkedHashMap<String, List<Map<String, Object>>>();

        var keys = new HashSet<>(firstFileMap.keySet());
        keys.addAll(secondFileMap.keySet());

        keys.stream().sorted().forEach(key -> {
            if (firstFileMap.containsKey(key) && secondFileMap.containsKey(key)) {
                if (isNull(firstFileMap.get(key)) & (isNull(secondFileMap.get(key)))
                        || nonNull(firstFileMap.get(key)) && nonNull(secondFileMap.get(key))
                        && firstFileMap.get(key).equals(secondFileMap.get(key))) {
                    Map<String, Object> diffMap = new HashMap<>();
                    diffMap.put(UNCHANGED_DIFF, firstFileMap.get(key));
                    diffsMap.put(key, List.of(diffMap));
                } else {
                    Map<String, Object> firstDiffMap = new HashMap<>();
                    firstDiffMap.put(DELETED_DIFF, firstFileMap.get(key));

                    Map<String, Object> secondDiffMap = new HashMap<>();
                    secondDiffMap.put(ADDED_DIFF, secondFileMap.get(key));

                    diffsMap.put(key, List.of(firstDiffMap, secondDiffMap));
                }
            } else if (firstFileMap.containsKey(key)) {
                diffsMap.put(key, List.of(Map.of(DELETED_DIFF, firstFileMap.get(key))));
            } else {
                diffsMap.put(key, List.of(Map.of(ADDED_DIFF, secondFileMap.get(key))));
            }
        });
        return diffsMap;
    }
}
