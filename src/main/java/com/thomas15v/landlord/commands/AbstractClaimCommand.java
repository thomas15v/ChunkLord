package com.thomas15v.landlord.commands;

import com.google.common.base.Optional;
import com.thomas15v.landlord.LandLordPlugin;
import com.thomas15v.landlord.protection.Claim;
import com.thomas15v.landlord.protection.Tenant;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;

/**
 * Created by thomas15v on 18/05/15.
 */
public abstract class AbstractClaimCommand extends AbstractCommand {


    protected LandLordPlugin plugin;

    public AbstractClaimCommand(LandLordPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            execute(plugin.getClaimManager().getClaimFor(player.getLocation()),
                    plugin.getTenantManager().getTentant(player).get(), args, player);
        }
        src.sendMessage(Texts.of("Only a player can execute this command!"));
        return CommandResult.empty();
    }

    public abstract CommandResult execute(Optional<Claim> claimOptional, Tenant tenant, CommandContext args, Player player) throws CommandException;
}
