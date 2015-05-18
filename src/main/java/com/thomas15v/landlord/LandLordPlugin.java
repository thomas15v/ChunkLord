package com.thomas15v.landlord;

import com.google.inject.Inject;
import com.thomas15v.landlord.commands.ClaimCommand;
import com.thomas15v.landlord.commands.ClaimInfoCommand;
import com.thomas15v.landlord.commands.LandLordCommand;
import com.thomas15v.landlord.commands.UnclaimCommand;
import com.thomas15v.landlord.config.PluginConfig;
import com.thomas15v.landlord.language.LanguageManager;
import com.thomas15v.landlord.listeners.PlayerListener;
import com.thomas15v.landlord.protection.ClaimManager;
import com.thomas15v.landlord.protection.TenantManager;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Plugin(id="LandLord", name = "LandLord", version = "0.1")
    public class LandLordPlugin {

    private static LandLordPlugin instance;
    private Game game;

    public static LandLordPlugin getInstance(){
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
        game.getEventManager().register(this, new PlayerListener(tenantManager));
    }

    private void registerCommands(Game game){
        game.getCommandDispatcher().register(this, new LandLordCommand(this).getSpec(), "landlord", "ld");
        game.getCommandDispatcher().register(this, new ClaimCommand(this).getSpec(), "claim", "lclaim");
        game.getCommandDispatcher().register(this, new UnclaimCommand(this).getSpec(), "unclaim", "lunclaim");
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
