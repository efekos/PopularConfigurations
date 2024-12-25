package dev.efekos.pc.spigot;

public class Placeholder {

    public static Placeholder of(String name,String value){
        return new Placeholder(name,value);
    }

    private Placeholder(String name,String value){
        this.name = name;
        this.value = value;
    }

    private final String name;
    private final String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String replace(String input){
        return input.replace("%"+name+"%",value);
    }

}
