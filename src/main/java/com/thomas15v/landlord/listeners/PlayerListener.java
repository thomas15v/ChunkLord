package com.thomas15v.landlord.listeners;

import com.thomas15v.landlord.protection.TenantManager;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.BlockBreakEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;

/**
 * Created by thomas15v on 17/05/15.
 */
public class PlayerListener {

    private TenantManager tenantManager;

    public PlayerListener(TenantManager tenantManager){
        this.tenantManager = tenantManager;
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event){
        tenantManager.updateOrCreateTentant(event.getUser());
    }

    @Subscribe
    public void onBlock(BlockBreakEvent event){
        System.out.println(event.getCause());
    }

}
