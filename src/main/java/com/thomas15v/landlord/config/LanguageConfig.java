package com.thomas15v.landlord.config;

import com.thomas15v.landlord.language.Messages;

import java.io.File;
import java.io.IOException;

/**
 * Created by thomas15v on 17/05/15.
 */
public class LanguageConfig extends GenericConfig {
    public LanguageConfig(File configFile) throws IOException {
        super(configFile, 1);
    }

    @Override
    protected void generateDefaults() {
        for (Messages message : Messages.values())
            getRoot().getNode(message.name()).setValue(message.getDefaultMessage());
        super.generateDefaults();
    }
}
