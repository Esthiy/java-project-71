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
    private static final String EXPECTED_STYLISH_PATH = "./src/test/resources/expected_stylish.txt";
    private static final String EXPECTED_STYLISH_SAME_FILE_PATH = "./src/test/resources/expected_stylish_same_file.txt";
    private static final String EXPECTED_PLAIN_FILE_PATH = "./src/test/resources/expected_plain.txt";

    @Test
    @DisplayName("Check two existing json files with stylish format")
    void differPositiveStylishTest() throws Exception {
        var actual = Differ.generate(JSON_FILE_PATH_1, JSON_FILE_PATH_2, STYLISH_FORMAT);
        String expected = Files.readString(Paths.get(EXPECTED_STYLISH_PATH));
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing json files with plain format")
    void differPositivePlainTest() throws Exception {
        var actual = Differ.generate(JSON_FILE_PATH_1, JSON_FILE_PATH_2, PLAIN_FORMAT);
        String expected = Files.readString(Paths.get(EXPECTED_PLAIN_FILE_PATH));
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing json files with json format")
    void differPositiveJsonTest() throws Exception {
        var actual = Differ.generate(JSON_FILE_PATH_1, JSON_FILE_PATH_2, JSON_FORMAT);
        String expected = Files.readString(Paths.get(EXPECTED_JSON_FILE_PATH));
        assertThat(actual).as("Differ generation result").isEqualToIgnoringWhitespace(expected);
    }

    @Test
    @DisplayName("Check same existing json files")
    void differSameJsonFilesTest() throws Exception {
        var actual = Differ.generate(JSON_FILE_PATH_1, JSON_FILE_PATH_1, STYLISH_FORMAT);
        String expected = Files.readString(Paths.get(EXPECTED_STYLISH_SAME_FILE_PATH));
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check two existing yaml files")
    void differPositiveYamlTest() throws Exception {
        var actual = Differ.generate(YAML_FILE_PATH_1, YAML_FILE_PATH_2);
        String expected = Files.readString(Paths.get(EXPECTED_STYLISH_PATH));
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check same existing yaml files")
    void differSameYamlFilesTest() throws Exception {
        var actual = Differ.generate(YAML_FILE_PATH_1, YAML_FILE_PATH_1, STYLISH_FORMAT);
        String expected = Files.readString(Paths.get(EXPECTED_STYLISH_SAME_FILE_PATH));
        assertThat(actual).as("Differ generation result").isEqualTo(expected);
    }

    @Test
    @DisplayName("Check non-existing json files")
    void differNonExistentFileTest() {
        var filePath = "non-existent.json";
        var thrown = catchThrowable(() -> Differ.generate(filePath, JSON_FILE_PATH_1, STYLISH_FORMAT));
        assertThat(thrown).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("Check differ with no-json file")
    void differExistentTxtFileTest() {
        var filePath = "./src/test/resources/file.txt";
        var thrown = catchThrowable(() -> Differ.generate(filePath, filePath, STYLISH_FORMAT));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check null filePath as arguments")
    void differNullFilesTest() {
        var thrown = catchThrowable(() -> Differ.generate(null, null));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Check differ with json and yaml file")
    void differExistentJsonYamlFilesTest() {
        var thrown = catchThrowable(() -> Differ.generate(YAML_FILE_PATH_1, JSON_FILE_PATH_1, STYLISH_FORMAT));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check differ for file without extension")
    void differFileWithoutExtensionTest() {
        var filePathWithoutExtension = JSON_FILE_PATH_1.replace(".json", "");
        var thrown = catchThrowable(() -> Differ.generate(JSON_FILE_PATH_1, filePathWithoutExtension, STYLISH_FORMAT));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Check differ with unsupported format")
    void differFilesWithUnsupportedFormatTest() {
        var thrown = catchThrowable(() -> Differ.generate(YAML_FILE_PATH_1, YAML_FILE_PATH_2, "test"));
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }
}
