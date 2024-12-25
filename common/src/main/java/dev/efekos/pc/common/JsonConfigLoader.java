package dev.efekos.pc.common;

import com.google.gson.*;
import dev.efekos.pc.common.tag.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonConfigLoader implements ConfigLoader {

    private final ErrorContext context = new ErrorContext();

    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .disableJdkUnsafe()
            .disableInnerClassSerialization()
            .setPrettyPrinting()
            .create();

    @Override
    public ConfigObject load(File file) {
        if(!file.exists()||!file.isFile()||!file.canRead()) throw new IllegalStateException("Cannot load config file: " + file);
        try {
            JsonObject json = GSON.fromJson(new FileReader(file), JsonObject.class);
            return readObject(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConfigObject readObject(JsonObject json) {
        ConfigObject object = new ConfigObject();

        String s = context.getKey();
        for (String key : json.keySet()) {
            context.setKey(s + "." + key);
            JsonElement element = json.get(key);
            if(element.isJsonNull())continue;
            object.set(key,readElement(element));
        }
        context.setKey(s);

        return object;
    }

    private ConfigElement readElement(JsonElement json) {
        if(json.isJsonPrimitive()){
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if(primitive.isString()) return ConfigString.of(primitive.getAsString());
            else if(primitive.isBoolean()) return ConfigBoolean.of(primitive.getAsBoolean());
            else {
                Number number = primitive.getAsNumber();
                if(number.doubleValue()!=number.intValue()) return ConfigDouble.of(number.doubleValue());
                else if(number.longValue()>Integer.MAX_VALUE) return ConfigLong.of(number.longValue());
                else return ConfigInteger.of(number.intValue());
            }
        } else if(json.isJsonObject()) return readObject(json.getAsJsonObject());
        else if(json.isJsonArray()){
            JsonArray array = json.getAsJsonArray();
            List<ConfigElement> list = new ArrayList<>();
            Class<?> c = null;
            String s = context.getKey();
            for (int i = 0; i < array.size(); i++) {
                context.setKey(s+"["+i+"]");
                JsonElement element = array.get(i);
                ConfigElement read = readElement(element);
                if(c==null)c=read.getClass();
                else if(read.getClass()!=c) throw new IllegalStateException("Config element '"+context.getKey()+"' is a type different than others elements in the list '"+s+"'");
                list.add(read);
            }
            return ConfigArray.of(list);
        } else throw new IllegalStateException("Config element '"+context.getKey()+"' is an unknown type.");
    }

}
