# Changelog

# `1`

## `1.0`

![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-dot.svg) Initial release, with `YamlConfig`,`JsonConfig` and `MessageProvider`s.

## `1.1`

![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `OptionLoader`, a new way to read options from a config with serialization.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `OptionType` with 36 builtin types.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `Option`s with default values, a type and a key.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `YamlOptionLoader`, `YamlConfig` implementation of an `OptionLoader`.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `YamlConfig#asOptionLoader` to create a `YamlOptionLoader` based on a config.

### `1.1.1`

![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `Component` prefixes to `MessageProvider`s of the Paper module.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `String` prefixes to `MessageProvider`s of the Spigot module.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `MessageProvider#formatRaw` and `MessageProvider#formatListRaw` to avoid prefixes when needed.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-plus.svg) Added `YamlConfig#asMessageProviderWithPrefix(Component)` for Paper and `YamlConfig#asMessageProviderWithPrefix(String)` for Spigot.\
![](https://raw.githubusercontent.com/efekos/efekos/refs/heads/main/icons/symbol-change.svg) Made `MessageProvider#format` and `MessageProvider#formatList` default methods that support prefixes. You must now implement raw formatters in your own `MessageProvider`s.