/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.pc;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Another type of configuration that can read JSON files.
 */
public class JsonConfig implements Config {

    private final String resourceName;

    private final Map<String, String> stringValues = new HashMap<>();
    private final Map<String, Integer> numberValues = new HashMap<>();
    private final Map<String, Double> doubleValues = new HashMap<>();
    private final Map<String, Boolean> booleanValues = new HashMap<>();
    private final Map<String, Character> characterValues = new HashMap<>();
    private final Map<String, List<Object>> arrayValues = new HashMap<>();
    private final List<String> nullValues = new ArrayList<>();

    private final JavaPlugin plugin;

    /**
     * Another type of configuration that can read JSON files.
     *
     * @param resourceName Name of the resource you want to use as this config. Make sure that your resource name ends with `.yml` and you have a resource with the exact same name on your 'resources' folder.
     * @param plugin       An instance of your plugin.
     */
    public JsonConfig(String resourceName, JavaPlugin plugin) {
        this.resourceName = resourceName.endsWith(".json") ? resourceName : resourceName + ".json";
        this.plugin = plugin;
    }


    /**
     * Overrides the config file with the default config file. Can be used for restoration purposes
     */
    public void override() {
        plugin.saveResource(resourceName, true);
        reload();
    }

    /**
     * Loads the default configuration file to your plugin's data folder. Recommended to use inside {@link JavaPlugin#onEnable()}.
     *
     * @throws FileNotFoundException when you don't have a default config file in your 'resources' folder.
     */
    public void setup() throws FileNotFoundException {
        File file = new File(plugin.getDataFolder(), resourceName);

        if (!file.exists()) {
            try {
                plugin.saveResource(resourceName, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JsonObject object = JsonParser.parseReader(new JsonReader(new FileReader(file))).getAsJsonObject();
        readObject("", object);
    }

    private void readObject(String preKey, JsonObject object) {
        Set<String> keys = object.keySet();

        for (String key : keys) {
            JsonElement element = object.get(key);

            if (element.isJsonNull()) {
                nullValues.add(preKey + key);
                continue;
            }

            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = (JsonPrimitive) element;
                if (primitive.isBoolean()) {
                    booleanValues.put(preKey + key, primitive.getAsBoolean());
                    continue;
                }

                if (primitive.isNumber()) {
                    readNumber(preKey, key, primitive);
                    continue;
                }

                if (primitive.isString()) {
                    readString(preKey, key, primitive);
                    continue;
                }

            } // if element is primitive

            if (element.isJsonObject()) {
                JsonObject jsonObject = (JsonObject) element;
                readObject(preKey + key + ".", jsonObject);
            }

            if (element.isJsonArray()) {
                List<JsonElement> list = ((JsonArray) element).asList();
                List<Object> listToSave = new ArrayList<>();


                for (JsonElement jsonElement : list) {
                    if (jsonElement.isJsonNull()) {
                        listToSave.add(null);
                        continue;
                    }
                    if (jsonElement.isJsonPrimitive()) {
                        JsonPrimitive primitive = (JsonPrimitive) jsonElement;
                        if (primitive.isBoolean()) {
                            listToSave.add(primitive.getAsBoolean());
                            continue;
                        }

                        if (primitive.isNumber()) {
                            listToSave.add(primitive.getAsNumber());
                            continue;
                        }

                        if (primitive.isString()) {
                            listToSave.add(jsonElement.getAsString());
                            continue;
                        }

                    }
                    if (jsonElement.isJsonObject()) {
                        listToSave.add(jsonElement.getAsJsonObject());
                    }
                    if (jsonElement.isJsonArray()) {
                        listToSave.add(jsonElement.getAsJsonArray());
                    }
                }

                arrayValues.put(preKey + key, listToSave);
            }

        } // string key : keys
    } // method end

    private void readNumber(String preKey, String key, JsonPrimitive primitive) {
        if ((double) primitive.getAsInt() != primitive.getAsDouble()) { // number should be a double
            doubleValues.put(preKey + key, primitive.getAsDouble());
        } else { // number can be double or integer, so storing the key to both maps.
            doubleValues.put(preKey + key, primitive.getAsDouble());
            numberValues.put(preKey + key, primitive.getAsInt());
        }
    }

    private void readString(String preKey, String key, JsonPrimitive primitive) {
        stringValues.put(preKey + key, primitive.getAsString());
        if (primitive.getAsString().length() == 1) characterValues.put(preKey + key, primitive.getAsString().charAt(0));
    }

    /**
     * Finds a {@link String} from the configuration
     *
     * @param path Path to the {@link String} you want.
     * @param def  Default {@link String} to be returned in case nothing found in path. There are good reasons behind this parameter being required.
     * @return Whatever found.
     */
    public String getString(String path, String def) {
        return stringValues.getOrDefault(path, def);
    }

    /**
     * Finds a {@link Boolean} from the configuration
     *
     * @param path Path to the {@link Boolean} you want.
     * @param def  Default {@link Boolean} to be returned in case nothing found in path. There are good reasons behind this parameter being required.
     * @return Whatever found.
     */
    public boolean getBoolean(String path, boolean def) {
        return booleanValues.getOrDefault(path, def);
    }

    /**
     * Finds a {@link Character} from the configuration
     *
     * @param path Path to the {@link Character} you want.
     * @param def  Default {@link Character} to be returned in case nothing found in path. There are good reasons behind this parameter being required.
     * @return Whatever found.
     */
    public Character getCharacter(String path, char def) {
        return characterValues.getOrDefault(path, def);
    }

    /**
     * Finds an {@link Integer} from the configuration
     *
     * @param path Path to the {@link Integer} you want.
     * @param def  Default {@link Integer} to be returned in case nothing found in path. There are good reasons behind this parameter being required.
     * @return Whatever found.
     */
    public int getInteger(String path, int def) {
        return numberValues.getOrDefault(path, def);
    }

    /**
     * Finds a {@link Double} from the configuration
     *
     * @param path Path to the {@link Double} you want.
     * @param def  Default {@link Double} to be returned in case nothing found in path. There are good reasons behind this parameter being required.
     * @return Whatever found.
     */
    public double getDouble(String path, double def) {
        return doubleValues.getOrDefault(path, def);
    }

    /**
     * Finds a list from config. Please note that:
     * <ul>
     *     <li>Every boolean inside an array was saved as {@link Boolean}</li>
     *     <li>Every string inside an array was saved as {@link String}</li>
     *     <li>Every number inside an array was saved as {@link Number}</li>
     *     <li>Every object inside an array was saved as {@link JsonObject}</li>
     *     <li>Every array inside an array was saved as {@link JsonArray}</li>
     * </ul>
     *
     * @param path Key of the value that you want.
     * @return A list if found, {@code null} otherwise.
     */
    public List<Object> getList(String path) {
        if (nullValues.contains(path)) return null;
        return arrayValues.get(path);
    }

    /**
     * Reloads the config by reading the file again.
     */
    public void reload() {
        arrayValues.clear();
        nullValues.clear();
        booleanValues.clear();
        doubleValues.clear();
        numberValues.clear();
        stringValues.clear();
        characterValues.clear();

        try {
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getStringList(String key) {
        List<Object> list = arrayValues.get(key);
        if (list instanceof List l) return (List<String>) l;
        else return new ArrayList<>();
    }

}
