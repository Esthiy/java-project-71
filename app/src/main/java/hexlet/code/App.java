package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Parameters(index = "0", paramLabel = "filePath1", description = "path to first file")
    String filePath1;

    @Parameters(index = "1", paramLabel = "filePath2", description = "path to second file")
    String filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    String format = "stylish";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Чтение файла:
        var path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        // Чтение файла:
        var path2 = Paths.get(filePath2).toAbsolutePath().normalize();
        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        Map<String, Object> fileMap2 = objectMapper.readValue(Files.readString(path2), new TypeReference<>() {
        });
        Map<String, Object> fileMap1 = objectMapper.readValue(Files.readString(path1), new TypeReference<>() {
        });

        var result = Differ.generate(fileMap1, fileMap2);
        System.out.println(result);
        return 0;
    }
}
