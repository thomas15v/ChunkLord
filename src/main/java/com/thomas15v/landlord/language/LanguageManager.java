package com.thomas15v.landlord.language;

import com.thomas15v.landlord.config.LanguageConfig;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.util.command.CommandSource;

import java.io.File;
import java.io.IOException;

/**
 * Created by thomas15v on 17/05/15.
 */
public class LanguageManager {

    private File languageFolder;
    private LanguageConfig languageConfig;

    public LanguageManager(File PluginFolder, String language){
        this.languageFolder = new File(PluginFolder, "languages");
        if (!languageFolder.exists())
            languageFolder.mkdirs();
        try {
            this.languageConfig = new LanguageConfig(new File(languageFolder, language + ".conf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(CommandSource src, Messages message, TextColor color, String... strings ){
        src.sendMessage(Texts.builder().color(color).
                append(get(message, strings)).
                build());
    }

    public Text get(Messages message, String... strings){
       return Texts.of(String.format(languageConfig.getRoot().getNode(message.name()).getString(), strings));
    }
}
