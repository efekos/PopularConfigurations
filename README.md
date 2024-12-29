![](assets/PopularConfigurations.png)
# PopularConfigurations

![](https://badgen.net/github/license/efekos/PopularConfigrations)
![](https://badgen.net/github/stars/efekos/PopularConfigrations)
![](https://badgen.net/github/release/efekos/PopularConfigrations)
![](https://badgen.net/github/releases/efekos/PopularConfigrations)
![](https://badgen.net/github/merged-prs/efekos/PopularConfigrations)
![](https://badgen.net/github/issues/efekos/PopularConfigrations)

<!-- TOC -->
* [PopularConfigurations](#popularconfigurations)
  * [Installation](#installation)
    * [Maven](#maven)
    * [Gradle](#gradle)
  * [Usage](#usage)
* [License](#license)
<!-- TOC -->

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

# Usage

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

## Options

Other than basic configuration classes and **MessageProviders**, this library has one more type of configuration,
**OptionLoader**. **OptionLoader**s only work with **YamlConfig**s. In order to use **OptionLoader**s, you have to
create **Option**s to load. Like this:

````java
import dev.efekos.pc.option.Option;
import dev.efekos.pc.option.OptionType;

import java.util.List;

public class MyPluginOptions {
    
    public static final Option<String> PREFIX = Option.of("prefix", OptionType.STRING, "&6MP: &r");
    public static final Option<Boolean> CLEAR_CACHE = Option.of("clear-cache.enabled", OptionType.BOOLEAN, true);
    public static final Option<Integer> CLEAR_CACHE_INTERVAL = Option.of("clear-cache.interval", OptionType.INTEGER, 10);
    public static final Option<List<OfflinePlayer>> MANAGERS = Option.of("managers", OptionType.OFFLINE_PLAYER.array(), List.of());
    
}
````

Then, you can use `YamlConfig#asOptionLoader()` to get an **OptionLoader** and use options like this:

````java

import dev.efekos.pc.option.OptionLoader;
import dev.efekos.mp.MyPluginOptions;

public class Utilities {
    
    public static OptionLoader OPTION_LOADER;
    
    public static String getPrefix(){
        return OPTION_LOADER.getOption(MyPluginOptions.PREFIX);   
    }
    
    public static boolean isCacheEnabled(){
        return OPTION_LOADER.getOption(MyPluginOptions.CLEAR_CACHE);
    }
    
    public static boolean isManager(OfflinePlayer player){
        return OPTION_LOADER.getOption(MyPluginOptions.MANAGERS).contains(player);
    }
    
}
````

You can also create your own implementations of **OptionType** if you can't find an **OptionType** for the class you
want to use. If the class you want to use isn't a custom class in your plugin, you can
[open an issue](https://github.com/efekos/PopularConfigurations/issues) to add it to builtin option types.

# License

This project is licensed with the [MIT License](./LICENSE.md).