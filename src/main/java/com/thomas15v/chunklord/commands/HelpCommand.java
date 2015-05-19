package com.thomas15v.chunklord.commands;

import com.thomas15v.chunklord.ChunkLordPlugin;
import com.thomas15v.chunklord.language.Messages;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;

/**
 * Created by thomas15v on 17/05/15.
 */
public class HelpCommand extends AbstractCommand {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        ChunkLordPlugin.getInstance().getLanguageManager().sendMessage(src, Messages.HELP_MESSAGE, TextColors.AQUA);
        return CommandResult.success();
    }
}
