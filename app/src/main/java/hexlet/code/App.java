package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

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
    public Integer call() {
        System.out.println("Hello world!");
        return 0;
    }
}
