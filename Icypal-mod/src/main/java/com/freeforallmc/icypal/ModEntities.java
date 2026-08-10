package com.freeforallmc.icypal;

import com.tuo.nome.entity.IcypalEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<IcypalEntity> ICYPAL = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier("icypal", "troll"),
        EntityType.Builder.create(IcypalEntity::new, SpawnGroup.MONSTER).dimensions(0.6f, 1.9f).build()
    );
}