package com.freeforallmc.icypal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcypalTrollMod implements ModInitializer {
    public static final String MOD_ID = "icypal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Icypal Troll Mod caricata per 26.2.3!");
        FabricDefaultAttributeRegistry.register(ModEntities.ICYPAL, IcypalEntity.createIcypalAttributes());
        PlayerJoinEvent.register(); // Attiva lo spawn quando entri
    }
}