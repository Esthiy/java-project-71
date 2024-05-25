package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hexlet.code.formatters.Formatter.JSON_FORMAT;
import static hexlet.code.formatters.Formatter.PLAIN_FORMAT;
import static hexlet.code.formatters.Formatter.STYLISH_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AppTest {

    private static final String JSON_FILE_PATH_1 = "./src/test/resources/file1.json";
    private static final String JSON_FILE_PATH_2 = "./src/test/resources/file2.json";
    private static final String YAML_FILE_PATH_1 = "./src/test/resources/file1.yml";
    private static final String YAML_FILE_PATH_2 = "./src/test/resources/file2.yaml";
    private static final String EXPECTED_JSON_FILE_PATH = "./src/test/resources/expected.json";

    @Test
    @DisplayName("Check two existing json files with stylish format")
    void differPositiveStylishTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_2, STYLISH_FORMAT);
        var actual = application.call();
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing json files with plain format")
    void differPositivePlainTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_2, PLAIN_FORMAT);
        var actual = application.call();
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing json files with json format")
    void differPositiveJsonTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_2, JSON_FORMAT);
        var actual = application.call();
        String expected = Files.readString(Paths.get(EXPECTED_JSON_FILE_PATH));
        assertThat(actual).as("Differ generation result").isEqualToIgnoringWhitespace(expected);
    }

    @Test
    @DisplayName("Check same existing json files")
    void differSameJsonFilesTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_1, STYLISH_FORMAT);
        var actual = application.call();
        String expected = """
                {
                    chars1: [a, b, c]
                    chars2: [d, e, f]
                    checked: false
                    default: null
                    id: 45
                    key1: value1
                    numbers1: [1, 2, 3, 4]
                    numbers2: [2, 3, 4, 5]
                    numbers3: [3, 4, 5]
                    setting1: Some value
                    setting2: 200
                    setting3: true
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing yaml files")
    void differPositiveYamlTest() throws Exception {
        var application = new App(YAML_FILE_PATH_1, YAML_FILE_PATH_2);
        var actual = application.call();
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check same existing yaml files")
    void differSameYamlFilesTest() throws Exception {
        var application = new App(YAML_FILE_PATH_1, YAML_FILE_PATH_1, STYLISH_FORMAT);
        var actual = application.call();
        String expected = """
                {
                    chars1: [a, b, c]
                    chars2: [d, e, f]
                    checked: false
                    default: null
                    id: 45
                    key1: value1
                    numbers1: [1, 2, 3, 4]
                    numbers2: [2, 3, 4, 5]
                    numbers3: [3, 4, 5]
                    setting1: Some value
                    setting2: 200
                    setting3: true
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check non-existing json files")
    void differNonExistentFileTest() {
        var filePath = "non-existent.json";
        var application = new App(filePath, JSON_FILE_PATH_1, STYLISH_FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("Check differ with no-json file")
    void differExistentTxtFileTest() {
        var filePath = "./src/test/resources/file.txt";
        var application = new App(filePath, filePath, STYLISH_FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check null filePath as arguments")
    void differNullFilesTest() {
        var application = new App();
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Check differ with json and yaml file")
    void differExistentJsonYamlFilesTest() {
        var application = new App(YAML_FILE_PATH_1, JSON_FILE_PATH_1, STYLISH_FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check differ for file without extension")
    void differFileWithoutExtensionTest() {
        var filePathWithoutExtension = JSON_FILE_PATH_1.replace(".json", "");
        var application = new App(JSON_FILE_PATH_1, filePathWithoutExtension, STYLISH_FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check differ with unsupported format")
    void differFilesWithUnsupportedFormatTest() {
        var application = new App(YAML_FILE_PATH_1, YAML_FILE_PATH_2, "test");
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }
}
