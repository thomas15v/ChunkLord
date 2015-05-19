package com.thomas15v.chunklord.protection;

import com.flowpowered.math.vector.Vector3i;
import com.thomas15v.chunklord.ChunkLordPlugin;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.entity.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Claim implements Serializable {

    @Setting
    private UUID owner;

    @Setting
    private long claimed;

    @Setting
    private List<UUID> trused;

    private TenantManager tenantManager;
    private Vector3i location;


    public Claim(Tenant tenant, Vector3i location){
        this(location);
        this.owner = tenant.getId();
        this.claimed = System.currentTimeMillis();
    }

    public Claim(Vector3i location) {
        this.location = location;
        this.tenantManager = ChunkLordPlugin.getInstance().getTenantManager();
        trused = new ArrayList<UUID>();
    }

    public Vector3i getLocation() {
        return location;
    }

    public Tenant getOwner(){
        return tenantManager.getTentant(owner).get();
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
        return getOwner().trusts(player.getUniqueId()) || trused.contains(player.getUniqueId()) || player.getUniqueId().equals(owner);
    }

}
