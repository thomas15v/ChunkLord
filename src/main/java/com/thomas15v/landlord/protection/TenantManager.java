package com.thomas15v.landlord.protection;

import com.google.common.base.Optional;
import com.thomas15v.landlord.config.TenantConfig;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by thomas15v on 17/05/15.
 */
public class TenantManager {

    private TenantConfig tentantConfig;

    public TenantManager(Game game, File pluginFolder) throws IOException {
        this.tentantConfig = new TenantConfig(new File(pluginFolder, "Tentants.conf"));
    }

    public Optional<Tenant> updateOrCreateTentant(Player player){
        try {
            Tenant tenant = tentantConfig.updateorCreateTentant(player);
            return Optional.of(tenant);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    public void updateOrCreateTentant(Tenant tenant){
        try {
            tentantConfig.updateorCreateTentant(tenant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Tenant> getTentant(Player player){
        return getTentant(player.getUniqueId());
    }

    public Optional<Tenant> getTentant(UUID id){
        return tentantConfig.getTentant(id);
    }

}
