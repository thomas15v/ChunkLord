package com.thomas15v.chunklord.listeners;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.thomas15v.chunklord.ChunkLordPlugin;
import com.thomas15v.chunklord.language.Messages;
import com.thomas15v.chunklord.protection.Claim;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.BlockChangeEvent;
import org.spongepowered.api.event.block.BulkBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractEvent;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by thomas15v on 18/05/15.
 */
public class WorldListener {

    private ChunkLordPlugin plugin;

    public WorldListener(ChunkLordPlugin plugin){
        this.plugin = plugin;
    }

    private boolean canAcces(Player player, Claim claim){
        if (claim.canAcces(player))
            return true;
        plugin.getLanguageManager().sendMessage(player, Messages.NO_ACCESS_TO_CLAIM, TextColors.RED, claim.getOwner().getName(), player.getName());
        Optional<Player> ownerOptional = claim.getOwner().getPlayer();
        if (ownerOptional.isPresent())
            plugin.getLanguageManager().sendMessage(ownerOptional.get(), Messages.UNAUTHORIZED_ACCESS_TO_CLAIM, TextColors.YELLOW, player.getName(), player.getName());
        return false;
    }

    private boolean canAcces(Player player, Location location){
        Optional<Claim> claimOptional = plugin.getClaimManager().getClaimFor(location);
        if (claimOptional.isPresent()){
            Claim claim = claimOptional.get();
            if (canAcces(player, claim))
                return canAcces(player, claim);
        }
        return true;
    }

    private Optional<Claim> getClaim(Location location){
        return plugin.getClaimManager().getClaimFor(location);
    }

    private Optional<Claim> getClaim(World world, Vector3i vector3i){
        return plugin.getClaimManager().getClaimFor(world, vector3i);
    }

    @Subscribe(ignoreCancelled = true)
    public void onBlockChange(BlockChangeEvent event){
        Optional<Claim> claim = getClaim(event.getBlock());
        if (event.getCause().isPresent() && claim.isPresent()){
            if (event.getCause().get().getCause() instanceof Player){
                Player player = (Player) event.getCause().get().getCause();
                if (canAcces(player, claim.get()))
                    return;
            }
            System.out.println("BlockChangeEvent BLOCKED!");
            event.setCancelled(true);
        }
    }

    //todo: test this when sponge is ready
    @Subscribe
    public void onBlocksChange(BulkBlockEvent event){
        System.out.println("HEY SPONGE IS READY :D!!!!!!!!!!!!!!");
        for (Location location : event.getBlocks()){
            event.filter(new Predicate<Location>() {
                @Override
                public boolean apply(Location input) {
                    Optional<Claim> claimOptional = getClaim(input);
                    return claimOptional.isPresent();
                }
            });
        }
    }

    @Subscribe(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event){
        if (event.getClickedPosition().isPresent() && event.getInteractionType() != EntityInteractionTypes.PICK_BLOCK &&
                event.getUser().getItemInHand().isPresent()) {
            Optional<Claim> claim = getClaim(event.getUser().getWorld(), event.getClickedPosition().get().toInt());
            if (claim.isPresent() && !canAcces(event.getUser(), claim.get())) {
                event.setCancelled(true);
                System.out.println("PlayerInteractEvent BLOCKED!");
            }

        }
    }
}
