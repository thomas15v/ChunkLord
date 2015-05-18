package com.thomas15v.landlord.protection;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Optional;
import com.thomas15v.landlord.config.ClaimsConfig;
import org.spongepowered.api.Game;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas15v on 16/05/15.
 */
public class ClaimManager {

    private Game game;
    private File worldfolder;
    private Map<String, ClaimsConfig> claimWorlds = new HashMap<String, ClaimsConfig>();

    public ClaimManager(Game game, File pluginFolder){
        this.game = game;
        this.worldfolder = new File(pluginFolder, "worlds");
        if (!worldfolder.exists())
            worldfolder.mkdirs();

        for (World world : game.getServer().getWorlds()){
            try {
                ClaimsConfig config = new ClaimsConfig(new File(worldfolder, world.getName() + ".conf"));
                claimWorlds.put(world.getName(), config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Optional<Claim> getClaimFor(Location loc){
        if (loc.getExtent() instanceof Chunk)
           return getClaimFor((Chunk) loc.getExtent());
        else if (loc.getExtent() instanceof World) {
           return getClaimFor((World) loc.getExtent(), loc.getBlockPosition());
        }
        return Optional.absent();
    }

    public Optional<Claim> getClaimFor(Chunk chunk){
        return getClaimFor(chunk.getWorld(), chunk.getPosition().getX(), chunk.getPosition().getZ());
    }

    public Optional<Claim> getClaimFor(World world, Vector3i loc){
        Vector3i chunk = loc.div(16);
        return getClaimFor(world, chunk.getX(), chunk.getZ());
    }

    public Optional<Claim> getClaimFor(World world, int X, int Z){
        return claimWorlds.get(world.getName()).getClaim(X, Z);
    }

    public void addClaim(World world, Claim claim){
        claimWorlds.get(world.getName()).setClaim(claim);
    }

    public void removeClaim(Location location){
        if (location.getExtent() instanceof World)
            removeClaim((World) location.getExtent(), location.getBlockPosition() );
        else if (location.getExtent() instanceof Chunk) {
            removeClaim((Chunk) location.getExtent());
        }
    }

    public void removeClaim(Chunk chunk){
        removeClaim(chunk.getWorld(), chunk.getPosition().getX(), chunk.getPosition().getZ());
    }

    public void removeClaim(World world, Vector3i loc){
        Vector3i chunk = loc.div(16);
        removeClaim(world, chunk.getX(), chunk.getZ());
    }

    public void removeClaim(World world, int X, int Z){
        claimWorlds.get(world.getName()).removeClaim(X, Z);
    }

    public void save(){
        for (ClaimsConfig config : claimWorlds.values())
            try {
                config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
