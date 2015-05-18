package com.thomas15v.landlord.commands;

import com.thomas15v.landlord.LandLordPlugin;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thomas15v on 17/05/15.
 */
public class LandLordCommand extends AbstractCommand {

    private LandLordPlugin plugin;

    public LandLordCommand(LandLordPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandSpec getSpec() {
        //todo: keep this updated
        HashMap<List<String>, CommandSpec> subcommands = new HashMap<List<String>, CommandSpec>();
        subcommands.put(Arrays.asList("help", "h", ""), new HelpCommand().getSpec());
        subcommands.put(Arrays.asList("claim"), new ClaimCommand(plugin).getSpec());
        subcommands.put(Arrays.asList("unclaim"), new UnclaimCommand(plugin).getSpec());
        subcommands.put(Arrays.asList("claiminfo"), new ClaimInfoCommand(plugin).getSpec());
        return CommandSpec.builder().children(subcommands).executor(this).build();
    }

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.success();
    }
}
