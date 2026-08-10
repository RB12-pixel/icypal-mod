package com.freeforallmc.icypal;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class PlayerJoinEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerWorld world = handler.player.getWorld();
            BlockPos pos = handler.player.getBlockPos().add(3, 0, 3); // spawna 3 blocchi davanti
            
            server.execute(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException e) {} // aspetta 2 sec
                IcypalEntity icypal = new IcypalEntity(ModEntities.ICYPAL, world);
                icypal.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                icypal.setCustomName(net.minecraft.text.Text.literal("Icypal"));
                world.spawnEntity(icypal);
            });
        });
    }
}