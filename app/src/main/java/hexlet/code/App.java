package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "app", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Calculate diff between two files")
public class App implements Callable<Integer> {
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
