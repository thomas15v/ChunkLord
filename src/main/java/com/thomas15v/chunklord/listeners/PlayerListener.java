package com.thomas15v.chunklord.listeners;

import com.thomas15v.chunklord.ChunkLordPlugin;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;

/**
 * Created by thomas15v on 17/05/15.
 */
public class PlayerListener {


    private ChunkLordPlugin plugin;

    public PlayerListener(ChunkLordPlugin plugin){

        this.plugin = plugin;
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event){
        plugin.getTenantManager().updateOrCreateTentant(event.getUser());
    }
}
