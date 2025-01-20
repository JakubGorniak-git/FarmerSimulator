import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private static SettingsManager instance;
    private final Map<String, String> settings;
    private static final String SETTINGS_FILE = "src/settings/settings.txt";

    private SettingsManager() {
        settings = new HashMap<>();
        loadSettings();
    }

    public static SettingsManager initialize() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    private void loadSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    settings.put(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }

    static public int getInt(String key) {
        System.err.println(key);
        return Integer.parseInt(instance.settings.get(key));
    }
}