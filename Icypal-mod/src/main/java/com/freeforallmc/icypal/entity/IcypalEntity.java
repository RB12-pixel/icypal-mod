package com.freeforallmc.icypal.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.UserCache;
import net.minecraft.world.World;

import java.util.Optional;

public class IcypalEntity extends HostileEntity {
    private int friendship = 0;
    
    public IcypalEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createIcypalAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }
    
    @Override
    public void onSpawn() {
        super.onSpawn();
        if (!this.getWorld().isClient) {
            MinecraftServer server = this.getWorld().getServer();
            if (server != null) {
                UserCache cache = server.getUserCache();
                Optional<GameProfile> profile = cache.findByName("Icypal"); // <-- CAMBIA QUI IL NOME
                profile.ifPresent(gameProfile -> {
                    this.setCustomName(Text.literal("Icypal").formatted(Formatting.AQUA));
                });
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        friendship -= 20;
        if (friendship < -50) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(15.0);
            this.setCustomName(Text.literal("Icypal ARABIATO").formatted(Formatting.RED));
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean interactMob(PlayerEntity player, net.minecraft.util.Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.DIAMOND)) {
            stack.decrement(1);
            friendship += 30;
            if (!this.getWorld().isClient) this.dropItem(Items.EMERALD);
            this.setCustomName(Text.literal("Icypal AMICO").formatted(Formatting.GREEN));
            return true;
        }
        return super.interactMob(player, hand);
    }
}