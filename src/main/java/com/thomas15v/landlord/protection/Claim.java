package com.thomas15v.landlord.protection;

import com.flowpowered.math.vector.Vector3i;
import com.thomas15v.landlord.LandLordPlugin;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.entity.player.Player;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Claim implements Serializable {

    @Setting
    private UUID tenantId;

    @Setting
    private long claimed;

    @Setting
    private List<UUID> trused;

    private TenantManager tenantManager;
    private Vector3i location;


    public Claim(Tenant tenant, TenantManager tenantManager, Vector3i location){
        this.tenantManager = tenantManager;
        this.location = location;
        this.tenantId = tenant.getId();
        this.claimed = System.currentTimeMillis();
    }

    public Claim(Vector3i location) {
        this.location = location;
        this.tenantManager = LandLordPlugin.getInstance().getTenantManager();
    }

    public Vector3i getLocation() {
        return location;
    }

    public Tenant getOwner(){
        return tenantManager.getTentant(tenantId).get();
    }

    public int getX(){
        return location.getX();
    }

    public int getZ(){
        return location.getZ();
    }

    public void addClaimTrust(UUID uuid){
        if (!trused.contains(uuid))
            trused.add(uuid);
    }

    public boolean canAcces(Player player){
        return getOwner().trusts(player.getUniqueId()) || trused.contains(player.getUniqueId());
    }

}
