package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Difference;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.isNull;

public class JsonFormatter {

    protected static String toFormat(LinkedHashMap<String, List<Difference>> diffsMap) {
        var jsonDiffsMap = new LinkedHashMap<String, JsonDifference>();
        diffsMap.keySet().forEach(key -> jsonDiffsMap.put(key, new JsonDifference(diffsMap.get(key))));
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(jsonDiffsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    protected static class JsonDifference {


        private final String diffType;
        private final List<Object> diffs;

        protected JsonDifference(List<Difference> differenceList) {
            if (differenceList.stream().anyMatch(it -> isNull(it.isLineAdded()))) {
                diffType = "unchanged";
            } else if (differenceList.stream().allMatch(Difference::isLineAdded)) {
                diffType = "added";
            } else if (differenceList.stream().noneMatch(Difference::isLineAdded)) {
                diffType = "removed";
            } else {
                diffType = "changed";
            }
            this.diffs = differenceList.stream().map(Difference::diffValue).toList();
        }

        public String getDiffType() {
            return diffType;
        }

        public List<Object> getDiffs() {
            return diffs;
        }
    }
}
