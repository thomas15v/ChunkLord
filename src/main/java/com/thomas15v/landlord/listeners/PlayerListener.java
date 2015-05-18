package com.thomas15v.landlord.listeners;

import com.google.common.base.Optional;
import com.thomas15v.landlord.protection.Claim;
import com.thomas15v.landlord.protection.ClaimManager;
import com.thomas15v.landlord.protection.TenantManager;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.BlockBreakEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;

/**
 * Created by thomas15v on 17/05/15.
 */
public class PlayerListener {

    private TenantManager tenantManager;
    private ClaimManager claimManager;

    public PlayerListener(TenantManager tenantManager, ClaimManager claimManager){
        this.tenantManager = tenantManager;
        this.claimManager = claimManager;
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event){
        tenantManager.updateOrCreateTentant(event.getUser());
    }

    @Subscribe(ignoreCancelled = true)
    public void onBlock(BlockBreakEvent event){
        Optional<Claim> claimOptional = claimManager.getClaimFor(event.getBlock());
        if (claimOptional.isPresent() && event.getCause().isPresent()){
            if (event.getCause().get() instanceof Player){
                Claim claim = claimOptional.get();
                Player player = (Player) event.getCause().get();
                if (claim.canAcces(player));
                    return;
            }
            event.setCancelled(true);
        }

    }

}
