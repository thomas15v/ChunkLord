package com.thomas15v.chunklord;

import com.google.inject.Inject;
import com.thomas15v.chunklord.commands.ClaimCommand;
import com.thomas15v.chunklord.commands.ClaimInfoCommand;
import com.thomas15v.chunklord.commands.ChunkLordCommand;
import com.thomas15v.chunklord.commands.UnclaimCommand;
import com.thomas15v.chunklord.config.PluginConfig;
import com.thomas15v.chunklord.language.LanguageManager;
import com.thomas15v.chunklord.listeners.PlayerListener;
import com.thomas15v.chunklord.listeners.WorldListener;
import com.thomas15v.chunklord.protection.ClaimManager;
import com.thomas15v.chunklord.protection.TenantManager;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.DefaultConfig;

import java.io.File;
import java.io.IOException;

@Plugin(id="ChunkLord", name = "ChunkLord", version = "0.1")
    public class ChunkLordPlugin {

    private static ChunkLordPlugin instance;
    private Game game;

    public static ChunkLordPlugin getInstance(){
        return instance;
    }

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    private PluginConfig config;
    private ClaimManager claimManager;
    private TenantManager tenantManager;
    private LanguageManager languageManager;

    @Subscribe
    public void onEnabled(ServerStartedEvent event){
        instance = this;
        if (!configDir.exists())
            configDir.mkdirs();
        try {
            this.config = new PluginConfig(defaultConfig);
            this.languageManager = new LanguageManager(configDir, config.getLanguage());
            this.claimManager = new ClaimManager(event.getGame(), configDir);
            this.tenantManager = new TenantManager(event.getGame(), configDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game = event.getGame();
        registerEvents(event.getGame());
        registerCommands(event.getGame());
    }

    private void registerEvents(Game game){
        game.getEventManager().register(this, new PlayerListener(this));
        game.getEventManager().register(this, new WorldListener(this));
    }

    private void registerCommands(Game game){
        game.getCommandDispatcher().register(this, new ChunkLordCommand(this).getSpec(), "chunklord", "cl");
        game.getCommandDispatcher().register(this, new ClaimCommand(this).getSpec(), "claim", "clclaim");
        game.getCommandDispatcher().register(this, new UnclaimCommand(this).getSpec(), "unclaim", "clunclaim");
        game.getCommandDispatcher().register(this, new ClaimInfoCommand(this).getSpec(), "claiminfo");
    }

    public void reload(){

    }

    public void onDisabe(ServerStoppingEvent event){
        claimManager.save();
    }

    public ClaimManager getClaimManager() {
        return claimManager;
    }

    public PluginConfig getConfig() {
        return config;
    }

    public TenantManager getTenantManager() {
        return tenantManager;
    }

    public Game getGame() {
        return game;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
