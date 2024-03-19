package hexlet.code;

import java.util.ArrayList;
import java.util.List;

public enum SupportedFileExtension {
    JSON(new ArrayList<>(List.of(".json"))),
    YAML(new ArrayList<>(List.of(".yaml", ".yml")));

    private final ArrayList<String> extensions;

    SupportedFileExtension(ArrayList<String> extension) {
        this.extensions = extension;
    }

    public ArrayList<String> getExtensions() {
        return extensions;
    }
}
