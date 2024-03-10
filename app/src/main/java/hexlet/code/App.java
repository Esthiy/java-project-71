package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<String> {

    @Parameters(index = "0", paramLabel = "filePath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filePath2", description = "path to second file")
    private String filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    public App(String filePath1, String filePath2, String format) {
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
        this.format = format;
    }

    public App() {
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public String call() throws Exception {
        return Differ.generate(filePath1, filePath2);
    }
}
