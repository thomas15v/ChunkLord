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
 * Created by thomas15v on 17/05/15.
 */
public class ClaimInfoCommand extends AbstractClaimCommand {

    private LanguageManager languageManager;

    public ClaimInfoCommand(ChunkLordPlugin plugin){
        super(plugin);
        this.languageManager = plugin.getLanguageManager();
        setDiscription("Displays Information about the claim");
    }

    @Override
    public CommandResult execute(Optional<Claim> claimOptional, Tenant tenant, CommandContext args, Player player) throws CommandException {
        if (claimOptional.isPresent()){
            Claim claim = claimOptional.get();
            languageManager.sendMessage(player, Messages.CLAIM_INFO, TextColors.AQUA,
                    player.getWorld().getName(),
                    String.valueOf(claim.getX()),
                    String.valueOf(claim.getZ()),
                    claim.getOwner().getName(),
                    "",
                    "");
        }else {
            languageManager.sendMessage(player, Messages.NO_CLAIM_FOUND, TextColors.YELLOW);
        }
        return CommandResult.success();
    }
}
