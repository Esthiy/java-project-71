package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AppTest {

    private static final String JSON_FILE_PATH_1 = "./src/test/resources/file1.json";
    private static final String JSON_FILE_PATH_2 = "./src/test/resources/file2.json";
    private static final String YAML_FILE_PATH_1 = "./src/test/resources/file1.yml";
    private static final String YAML_FILE_PATH_2 = "./src/test/resources/file2.yaml";
    private static final String FORMAT = "stylish";

    @Test
    @DisplayName("Check two existing json files")
    void differPositiveJsonTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_2, FORMAT);
        var actual = application.call();
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check same existing json files")
    void differSameJsonFilesTest() throws Exception {
        var application = new App(JSON_FILE_PATH_1, JSON_FILE_PATH_1, FORMAT);
        var actual = application.call();
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing yaml files")
    void differPositiveYamlTest() throws Exception {
        var application = new App(YAML_FILE_PATH_1, YAML_FILE_PATH_2, FORMAT);
        var actual = application.call();
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check same existing yaml files")
    void differSameYamlFilesTest() throws Exception {
        var application = new App(YAML_FILE_PATH_1, YAML_FILE_PATH_1, FORMAT);
        var actual = application.call();
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }
                """;
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check non-existing json files")
    void differNonExistentFileTest() {
        var filePath = "non-existent.json";
        var application = new App(filePath, JSON_FILE_PATH_1, FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("Check differ with no-json file")
    void differExistentTxtFileTest() {
        var filePath = "./src/test/resources/file.txt";
        var application = new App(filePath, filePath, FORMAT);
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
        var application = new App(YAML_FILE_PATH_1, JSON_FILE_PATH_1, FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check differ for file without extension")
    void differFileWithoutExtensionTest() {
        var filePathWithoutExtension = JSON_FILE_PATH_1.replace(".json", "");
        var application = new App(JSON_FILE_PATH_1, filePathWithoutExtension, FORMAT);
        var thrown = catchThrowable(application::call);
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
