package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import static hexlet.code.ExtensionUtil.getFileExtension;
import static hexlet.code.Parser.readFileIntoMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Differ {

    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        var extension1 = getFileExtension(filePath1);
        var extension2 = getFileExtension(filePath2);

        if (!extension1.equalsIgnoreCase(extension2)) {
            throw new IllegalArgumentException("Files extensions are not the same");
        }

        var fileMap1 = readFileIntoMap(filePath1);
        var fileMap2 = readFileIntoMap(filePath2);

        var diffsMap = new LinkedHashMap<String, List<Difference>>();
        var keys = new HashSet<>(fileMap1.keySet());
        keys.addAll(fileMap2.keySet());

        keys.stream().sorted().forEach(key -> {
            if (fileMap1.containsKey(key) && fileMap2.containsKey(key)) {
                if (isNull(fileMap1.get(key)) & (isNull(fileMap2.get(key)))
                        || nonNull(fileMap1.get(key)) && nonNull(fileMap2.get(key))
                        && fileMap1.get(key).equals(fileMap2.get(key))) {
                    diffsMap.put(key, List.of(new Difference('=', fileMap1.get(key))));
                } else {
                    var diffList = new ArrayList<Difference>();
                    diffList.add(new Difference('-', fileMap1.get(key)));
                    diffList.add(new Difference('+', fileMap2.get(key)));
                    diffsMap.put(key, diffList);
                }
            } else if (fileMap1.containsKey(key)) {
                diffsMap.put(key, List.of(new Difference('-', fileMap1.get(key))));
            } else {
                diffsMap.put(key, List.of(new Difference('+', fileMap2.get(key))));
            }
        });

        return Formatter.toFormat(format, diffsMap);
    }
}
