package com.thomas15v.landlord.protection;

import com.google.common.base.Optional;
import com.thomas15v.landlord.LandLordPlugin;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.entity.player.Player;

import java.util.*;

public class Tenant {

    @Setting
    private String name;

    @Setting
    private long lastJoined;

    //todo: change this to a Set<String> when configure it capable of it.
    @Setting
    private List<String> trusts;

    private UUID id;
    private TenantManager tenantManager;

    public Tenant(Player player, TenantManager tenantManager){
        this.tenantManager = tenantManager;
        this.id = player.getUniqueId();
        this.name = player.getName();
        this.lastJoined = System.currentTimeMillis();
        this.trusts = new ArrayList<String>();
    }

    public Tenant(){
        tenantManager = LandLordPlugin.getInstance().getTenantManager();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addTrust(Tenant tenant){
        if (!trusts.contains(tenant))
            trusts.add(tenant.getId().toString());
        tenantManager.updateOrCreateTentant(this);
    }

    public boolean trusts(UUID id){
        return trusts.contains(id);
    }

    public List<Tenant> getTrusts(){
        List<Tenant> trustlist = new ArrayList<Tenant>();
        for (String trust : this.trusts)
            trustlist.add(tenantManager.getTentant(UUID.fromString(trust)).get());
        return trustlist;
    }

    public Optional<Player> getPlayer() {
        return LandLordPlugin.getInstance().getGame().getServer().getPlayer(id);
    }
}
