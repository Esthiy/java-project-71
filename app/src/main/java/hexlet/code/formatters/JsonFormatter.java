package hexlet.code.formatters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {

    protected static String toFormat(LinkedHashMap<String, List<Map<String, Object>>> diffsMap) {
        var jsonDiffsMap = new ArrayList<JsonDifference>();
        diffsMap.keySet().forEach(key -> jsonDiffsMap.add(new JsonDifference(key, diffsMap.get(key))));
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(jsonDiffsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected static class JsonDifference {

        private final String key;
        private final String type;
        private Object value;
        private Object value1;
        private Object value2;

        protected JsonDifference(String key, List<Map<String, Object>> diffList) {
            this.key = key;
            if (diffList.size() > 1) {
                type = "CHANGED";
                value1 = diffList.get(0).values().iterator().next();
                value2 = diffList.get(1).values().iterator().next();
            } else {
                type = diffList.get(0).keySet().iterator().next();
                value = diffList.get(0).values().iterator().next();
            }
        }

        public String getKey() {
            return key;
        }

        public String getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public Object getValue1() {
            return value1;
        }

        public Object getValue2() {
            return value2;
        }
    }

//    {
//            "key": "id",
//            "type": "CHANGED",
//            "value1": 45,
//            "value2": null
//    }
}
