package com.thomas15v.landlord.commands;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 17/05/15.
 */
public abstract class AbstractCommand implements CommandExecutor{

    private String discription = "";

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public CommandSpec getSpec(){
        return CommandSpec.builder().description(Texts.of(discription)).executor(this).build();
    }

}
