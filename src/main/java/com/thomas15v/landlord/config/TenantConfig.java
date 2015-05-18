package com.thomas15v.landlord.config;

import com.google.common.base.Optional;
import com.thomas15v.landlord.protection.Tenant;
import com.thomas15v.landlord.protection.TenantManager;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by thomas15v on 17/05/15.
 */
public class TenantConfig extends GenericConfig {

    private int increment;

    private ObjectMapper<Tenant> mapper;

    public TenantConfig(File configFile) throws IOException {
        super(configFile, 1);
        try {
            mapper = ObjectMapper.forClass(Tenant.class);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public Tenant updateorCreateTentant(Player player, TenantManager manager) throws IOException {
        return updateorCreateTentant(new Tenant(player, manager));
    }

    public Optional<Tenant> getTentant(UUID uuid, TenantManager manager){
        try {
            Tenant tenant = mapper.bindToNew().populate(getRoot().getNode(uuid.toString()));
            tenant.setId(uuid);
            return Optional.fromNullable(tenant);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    public Tenant updateorCreateTentant(Tenant tenant) throws IOException {
        try {
            mapper.bind(tenant).serialize(getRoot().getNode(tenant.getId().toString()));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        save();
        return tenant;
    }
}
