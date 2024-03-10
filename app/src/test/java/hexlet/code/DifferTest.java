package hexlet.code;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DifferTest {

    private static final String FILE_PATH_1 = "./src/test/resources/file1.json";
    private static final String FILE_PATH_2 = "./src/test/resources/file2.json";

    @Test
    @DisplayName("Check two existing json files")
    void differPositiveTest() throws Exception {
        var actual = Differ.generate(FILE_PATH_1, FILE_PATH_2);
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
    void differSameFilesTest() throws Exception {
        var actual = Differ.generate(FILE_PATH_1, FILE_PATH_1);
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
        var thrown = catchThrowable(
                () -> Differ.generate(filePath, FILE_PATH_2)
        );
        assertThat(thrown).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("Check differ with no-json file")
    void differExistentTxtFileTest() {
        var filePath = "./src/test/resources/file.txt";
        var thrown = catchThrowable(
                () -> Differ.generate(filePath, FILE_PATH_2)
        );
        assertThat(thrown).isInstanceOf(JsonParseException.class);
    }
}
