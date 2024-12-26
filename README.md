![](assets/PopularConfigurations.png)
# PopularConfigurations

![](https://badgen.net/github/license/efekos/PopularConfigrations)
![](https://badgen.net/github/stars/efekos/PopularConfigrations)
![](https://badgen.net/github/release/efekos/PopularConfigrations)
![](https://badgen.net/github/releases/efekos/PopularConfigrations)
![](https://badgen.net/github/merged-prs/efekos/PopularConfigrations)
![](https://badgen.net/github/issues/efekos/PopularConfigrations)

A lightweight, basic configuration library to use in Spigot or Paper plugins.

## Installation

### Maven

1. Add this repository if you don't have it:

````xml
<repository>
    <id>efekosdev</id>
    <url>https://efekos.dev/maven</url>
</repository>
````

2. Add this dependency. Use the latest release as the version. You can also replace `pc-paper` with `pc-spigot` if you use Spigot.

````xml
<dependency>
    <groupId>dev.efekos.pc</groupId>
    <artifactId>pc-paper</artifactId>
    <version>1.0</version>
</dependency>
````

### Gradle

1. Add this repository if you don't have it.

````gradle
maven { url = 'https://efekos.dev/maven' }
````

2. Add this dependency. Use the latest release as the version. You can also replace `pc-paper` with `pc-spigot` if you use Spigot.

````gradle
implementation 'dev.efekos.pc:pc-paper:1.0'
````

## Usage

There are currently three types of configurations in PopularConfigurations. **JsonConfig**s, **YamlConfig**s and
**MessageProvider**s. YAML and JSON config classes are pretty self-explanatory, they are the same thing but in different
formats. **MessageProvider**s on the other hand, lets you translate colors and apply placeholders with ease. Here is a 
full example of both a normal config and a **MessageProvider**.

````java

import dev.efekos.pc.YamlConfig;
import dev.efekos.pc.Placeholder;
import dev.efekos.pc.MessagePortionHider;

import java.util.List;

public class MyPlugin extends JavaPlugin {

    private YamlConfig config;
    private static MessageProvider provider;

    public static String format(String key, Placeholder... placeholders) {
        return provider.format(key, placeholders);
    }

    public static List<String> format(String key, MessagePortionHider hider, Placeholder... placeholders) {
        return provider.formatList(key, hider, placeholders);
    }

    @Override
    public void onEnable() {
        config = new YamlConfig("config.yml", this);
        config.setup();
        if (isDebugModeEnabled()) config.override();

        JsonConfig messageConfig = new JsonConfig("messages.json", this);
        messageConfig.setup();
        provider = messageConfig.asMessageProvider();
    }

    public boolean isDebugModeEnabled() {
        //
    }

}
````