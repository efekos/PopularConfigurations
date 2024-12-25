package dev.efekos.pc.common;

import dev.efekos.pc.common.tag.ConfigObject;

import java.io.File;

public interface ConfigLoader {

    ConfigLoader JSON = new JsonConfigLoader();

    ConfigObject load(File file);

}