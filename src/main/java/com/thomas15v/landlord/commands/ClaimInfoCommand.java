package com.thomas15v.landlord.commands;

import com.flowpowered.math.vector.Vector3i;
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
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by thomas15v on 17/05/15.
 */
public class ClaimInfoCommand implements Command {

    private LanguageManager languageManager;
    private LandLordPlugin plugin;

    public ClaimInfoCommand(LandLordPlugin plugin){
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder().setDescription(Texts.of("Displays Information about the claim")).setExecutor(this).build();
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
                languageManager.sendMessage(src, Messages.CLAIM_INFO, TextColors.AQUA,
                        Texts.of(player.getWorld().getName()),
                        Texts.of(claim.getX()),
                        Texts.of(claim.getZ()),
                        Texts.of(claim.getOwner().getName())
                        );
            }else {
                languageManager.sendMessage(src, Messages.NO_CLAIM_FOUND, TextColors.YELLOW);
            }
            return CommandResult.success();
        }
        else {
            return CommandResult.empty();
        }
    }
}
