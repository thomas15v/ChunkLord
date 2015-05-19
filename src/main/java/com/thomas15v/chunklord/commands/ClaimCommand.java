package com.thomas15v.chunklord.commands;

import com.google.common.base.Optional;
import com.thomas15v.chunklord.ChunkLordPlugin;
import com.thomas15v.chunklord.language.LanguageManager;
import com.thomas15v.chunklord.language.Messages;
import com.thomas15v.chunklord.protection.Claim;
import com.thomas15v.chunklord.protection.Tenant;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.args.CommandContext;

/**
 * Created by thomas15v on 16/05/15.
 */
public class ClaimCommand extends AbstractClaimCommand {

    private LanguageManager languageManager;

    public ClaimCommand(ChunkLordPlugin plugin) {
        super(plugin);
        this.languageManager = plugin.getLanguageManager();
        setDiscription("Claim a chunk for yourself!");
    }

    @Override
    public CommandResult execute(Optional<Claim> claimOptional, Tenant tenant, CommandContext args, Player player) throws CommandException {;
        if (claimOptional.isPresent()){
            languageManager.sendMessage(player, Messages.ALREADY_CLAIMED_BY, TextColors.RED,
                    claimOptional.get().getOwner().getName());
        }else {
            plugin.getClaimManager().addClaim(player.getWorld(), new Claim(tenant, player.getLocation().getBlockPosition().div(16)));
            languageManager.sendMessage(player, Messages.LAND_CLAIMED, TextColors.GREEN);
        }
        return CommandResult.success();
    }
}
