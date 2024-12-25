package dev.efekos.pc.paper;

import java.util.List;

public interface Config {

    List<String> getStringList(String key);
    String getString(String key,String def);
    boolean getBoolean(String key,boolean def);
    int getInteger(String key,int def);
    double getDouble(String key,double def);
    void reload();
    void override();
    default ConfigMessageProvider asMessageProvider() {
        return new ConfigMessageProvider(this);
    }

}
