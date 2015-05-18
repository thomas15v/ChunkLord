package com.thomas15v.landlord.commands;

import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 17/05/15.
 */
public interface Command extends CommandExecutor{

   CommandSpec getSpec();

}
