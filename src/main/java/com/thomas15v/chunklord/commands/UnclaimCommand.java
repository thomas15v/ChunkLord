package com.thomas15v.chunklord.commands;

import com.google.common.base.Optional;
import com.thomas15v.chunklord.ChunkLordPlugin;
import com.thomas15v.chunklord.language.Messages;
import com.thomas15v.chunklord.protection.Claim;
import com.thomas15v.chunklord.protection.Tenant;
import com.thomas15v.chunklord.util.Permissions;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.args.CommandContext;

/**
 * Created by thomas15v on 16/05/15.
 */
public class UnclaimCommand extends AbstractClaimCommand {

    public UnclaimCommand(ChunkLordPlugin plugin){
        super(plugin);
        setDiscription("unclaims a chunk");
    }

    @Override
    public CommandResult execute(Optional<Claim> claimOptional, Tenant tenant, CommandContext args, Player player) throws CommandException {
        if (claimOptional.isPresent()){
            Claim claim = claimOptional.get();
            try {
                if (claim.getOwner().getId().equals(player.getUniqueId()) || player.hasPermission(Permissions.ADMINUNCLAIM))
                {
                    plugin.getClaimManager().removeClaim(player.getLocation());
                    plugin.getLanguageManager().sendMessage(player, Messages.LAND_UNCLAIMED, TextColors.GREEN);
                }
                else
                    plugin.getLanguageManager().sendMessage(player, Messages.NO_ACCES_TO_UNCLAIM, TextColors.RED);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            plugin.getLanguageManager().sendMessage(player, Messages.NO_CLAIM_TO_UNCLAIM, TextColors.YELLOW);
        }
        return CommandResult.success();
    }
}
