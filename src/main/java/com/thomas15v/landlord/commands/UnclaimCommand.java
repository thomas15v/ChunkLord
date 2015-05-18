package com.thomas15v.landlord.commands;

import com.google.common.base.Optional;
import com.thomas15v.landlord.LandLordPlugin;
import com.thomas15v.landlord.language.Messages;
import com.thomas15v.landlord.protection.Claim;
import com.thomas15v.landlord.protection.Tenant;
import com.thomas15v.landlord.util.Permissions;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 16/05/15.
 */
public class UnclaimCommand implements Command {

    private LandLordPlugin plugin;

    public UnclaimCommand(LandLordPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder().description(Texts.of("unclaims a chunk")).executor(this).build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            Tenant tenant = plugin.getTenantManager().getTentant(player).get();
            Optional<Claim> claimOptional = plugin.getClaimManager().getClaimFor(player.getLocation());
            if (claimOptional.isPresent()){
                Claim claim = claimOptional.get();
                try {
                    if (claim.getOwner().getId().equals(player.getUniqueId()) || player.hasPermission(Permissions.ADMINUNCLAIM))
                    {
                        plugin.getClaimManager().removeClaim(player.getLocation());
                        plugin.getLanguageManager().sendMessage(src, Messages.LAND_UNCLAIMED, TextColors.GREEN);
                    }
                    else
                        plugin.getLanguageManager().sendMessage(src, Messages.NO_ACCES_TO_UNCLAIM, TextColors.RED);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                plugin.getLanguageManager().sendMessage(src, Messages.NO_CLAIM_TO_UNCLAIM, TextColors.YELLOW);
            }
            return CommandResult.success();
        }
        else {
            return CommandResult.empty();
        }
    }
}
