package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<String> {

    @Parameters(index = "0", paramLabel = "filePath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filePath2", description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]",
            defaultValue = "stylish")
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public String call() throws Exception {
        var result = Differ.generate(filePath1, filePath2, format);
        System.out.println(result);
        return result;
    }
}
