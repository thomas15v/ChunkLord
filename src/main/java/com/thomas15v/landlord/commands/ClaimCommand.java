package com.thomas15v.landlord.commands;

import com.google.common.base.Optional;
import com.thomas15v.landlord.LandLordPlugin;
import com.thomas15v.landlord.language.LanguageManager;
import com.thomas15v.landlord.language.Messages;
import com.thomas15v.landlord.protection.Claim;
import com.thomas15v.landlord.protection.Tenant;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandArgs;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 16/05/15.
 */
public class ClaimCommand implements Command {

    private LandLordPlugin plugin;
    private LanguageManager languageManager;

    public ClaimCommand(LandLordPlugin plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            Tenant tenant = plugin.getTenantManager().getTentant(player).get();
            Optional<Claim> claimOptional = plugin.getClaimManager().getClaimFor(player.getLocation());
            if (claimOptional.isPresent()){
                languageManager.sendMessage(src, Messages.ALREADY_CLAIMED_BY, TextColors.RED,
                        claimOptional.get().getOwner().getName());
            }else {
                plugin.getClaimManager().addClaim(player.getWorld(), new Claim(tenant, plugin.getTenantManager(), player.getLocation().getBlockPosition().div(16)));
                languageManager.sendMessage(src, Messages.LAND_CLAIMED, TextColors.GREEN);
            }
            return CommandResult.success();
        }
        else {
            return CommandResult.empty();
        }
    }

    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder().description(Texts.of("Claims a chunk for yourself!")).executor(this).build();
    }
}
