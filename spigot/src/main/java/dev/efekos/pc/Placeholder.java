package dev.efekos.pc;

public class Placeholder {

    private final String name;
    private final String value;

    private Placeholder(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Placeholder of(String name, String value) {
        return new Placeholder(name, value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String replace(String input) {
        return input.replace("%" + name + "%", value);
    }

}
