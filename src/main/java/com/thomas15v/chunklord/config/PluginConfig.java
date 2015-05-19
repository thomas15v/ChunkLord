package com.thomas15v.chunklord.config;

import java.io.File;
import java.io.IOException;

/**
 * Created by thomas15v on 16/05/15.
 */
public class PluginConfig extends GenericConfig {

    private final String LANGUAGE = "Language";

    public PluginConfig(File configFile) throws IOException {
        super(configFile, 1);
    }

    @Override
    protected void generateDefaults() {
        getRoot().getNode(LANGUAGE).setValue("english");
        super.generateDefaults();
    }

    public String getLanguage() {
        return getRoot().getNode(LANGUAGE).getString();
    }
}
