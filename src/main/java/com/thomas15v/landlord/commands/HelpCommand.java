package com.thomas15v.landlord.commands;

import com.thomas15v.landlord.LandLordPlugin;
import com.thomas15v.landlord.language.Messages;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 17/05/15.
 */
public class HelpCommand implements Command {

    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder().executor(this).build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        LandLordPlugin.getInstance().getLanguageManager().sendMessage(src, Messages.HELP_MESSAGE, TextColors.AQUA);
        return CommandResult.success();
    }
}
