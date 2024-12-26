package dev.efekos.pc;

import java.util.List;

/**
 * Represents a configuration file.
 */
public interface Config {

    /**
     * Tries to find a list of strings in this config. Will return an empty list if the list wasn't found.
     * @param key Input key.
     * @return Whatever found.
     */
    List<String> getStringList(String key);

    /**
     * Tries to find a string in this config.
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    String getString(String key, String def);

    /**
     * Tries to find a boolean in this config.
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    boolean getBoolean(String key, boolean def);

    /**
     * Tries to find an integer in this config.
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    int getInteger(String key, int def);

    /**
     * Tries to find a double in this config.
     * @param key Input key.
     * @param def Default value to return if the result was {@code null}.
     * @return Whatever found.
     */
    double getDouble(String key, double def);

    /**
     * Reloads the configuration from its specified files.
     */
    void reload();

    /**
     * Overrides the configuration with the default config. Can be used for restoration and debug purposes.
     */
    void override();

    /**
     * Creates a message provider from this config.
     * @return New message provider.
     */
    default MessageProvider asMessageProvider() {
        return new ConfigMessageProvider(this);
    }

}
