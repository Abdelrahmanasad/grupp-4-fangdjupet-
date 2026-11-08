package se.fangdjupet.game.save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Properties;

/**
 * The contents of a save file: simple {@code key=value} lines, read and written
 * with typed methods so that the parsing code exists in one place only.
 * <p>
 * Numbers are written with {@link Double#toString(double)} and read with
 * {@link Double#parseDouble(String)}. Both always use a dot as decimal separator,
 * so a file saved on a Swedish computer ({@code 75,5}) can be loaded anywhere.
 */
final class SaveData {

    private final Properties properties;

    SaveData() {
        this(new Properties());
    }

    private SaveData(Properties properties) {
        this.properties = properties;
    }

    void put(String key, String value) {
        properties.setProperty(key, Objects.requireNonNull(value, "Value cannot be null"));
    }

    void put(String key, int value) {
        put(key, Integer.toString(value));
    }

    void put(String key, double value) {
        put(key, Double.toString(value));
    }

    void put(String key, Enum<?> value) {
        put(key, value.name());
    }

    boolean has(String key) {
        return properties.containsKey(key);
    }

    String getString(String key) throws SaveGameException {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new SaveGameException("Missing value in save file: " + key);
        }

        return value;
    }

    int getInt(String key) throws SaveGameException {
        try {
            return Integer.parseInt(getString(key).trim());
        } catch (NumberFormatException e) {
            throw new SaveGameException("Not a whole number in save file: " + key, e);
        }
    }

    double getDouble(String key) throws SaveGameException {
        double value;

        try {
            value = Double.parseDouble(getString(key).trim());
        } catch (NumberFormatException e) {
            throw new SaveGameException("Not a number in save file: " + key, e);
        }

        // parseDouble accepts "NaN" and "Infinity", which no stat should ever be
        if (!Double.isFinite(value)) {
            throw new SaveGameException("Invalid number in save file: " + key);
        }

        return value;
    }

    <E extends Enum<E>> E getEnum(String key, Class<E> type) throws SaveGameException {
        String value = getString(key).trim();

        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            throw new SaveGameException("Unknown " + type.getSimpleName() + " in save file: " + value, e);
        }
    }

    /**
     * Writes the data to a file. Writes to a temporary file first and then
     * replaces the real file, so a crash in the middle never destroys the old save.
     */
    void writeTo(Path file) throws SaveGameException {
        Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");

        try {
            Path parent = file.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
                properties.store(writer, "Fangdjupet save file");
            }

            Files.move(tempFile, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new SaveGameException("Could not write save file: " + file, e);
        }
    }

    static SaveData readFrom(Path file) throws SaveGameException {
        Properties properties = new Properties();

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (NoSuchFileException e) {
            throw new SaveGameException("No saved game found: " + file, e);
        } catch (IOException | IllegalArgumentException e) {
            // Properties.load throws IllegalArgumentException for broken escape sequences
            throw new SaveGameException("Could not read save file: " + file, e);
        }

        return new SaveData(properties);
    }
}