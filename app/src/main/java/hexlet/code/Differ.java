package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.util.ArrayList;
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

    private static LinkedHashMap<String, List<Difference>> generateDiffMap(Map<String, Object> firstFileMap,
                                                                           Map<String, Object> secondFileMap) {
        var diffsMap = new LinkedHashMap<String, List<Difference>>();
        var keys = new HashSet<>(firstFileMap.keySet());
        keys.addAll(secondFileMap.keySet());

        keys.stream().sorted().forEach(key -> {
            if (firstFileMap.containsKey(key) && secondFileMap.containsKey(key)) {
                if (isNull(firstFileMap.get(key)) & (isNull(secondFileMap.get(key)))
                        || nonNull(firstFileMap.get(key)) && nonNull(secondFileMap.get(key))
                        && firstFileMap.get(key).equals(secondFileMap.get(key))) {
                    diffsMap.put(key, List.of(new Difference(null, firstFileMap.get(key))));
                } else {
                    var diffList = new ArrayList<Difference>();
                    diffList.add(new Difference(false, firstFileMap.get(key)));
                    diffList.add(new Difference(true, secondFileMap.get(key)));
                    diffsMap.put(key, diffList);
                }
            } else if (firstFileMap.containsKey(key)) {
                diffsMap.put(key, List.of(new Difference(false, firstFileMap.get(key))));
            } else {
                diffsMap.put(key, List.of(new Difference(true, secondFileMap.get(key))));
            }
        });
        return diffsMap;
    }
}
