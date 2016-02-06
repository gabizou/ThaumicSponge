package com.gabizou.thaumicsponge.mixin.core.config;

import com.gabizou.thaumicsponge.api.entity.ThaumicEntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.common.registry.RegistryHelper;
import org.spongepowered.common.registry.type.entity.EntityTypeRegistryModule;
import thaumcraft.common.config.ConfigEntities;

@Mixin(ConfigEntities.class)
public class MixinConfigEntities {

    @Inject(method = "init", at = @At("RETURN"))
    private static void onInitEntities(CallbackInfo callbackInfo) {
        EntityTypeRegistryModule instance = EntityTypeRegistryModule.getInstance();
        registerEntityType(instance, "AURA_NODE", "auranode");
        registerEntityType(instance, "TAINT_CLOUD", "taintcloud");
        registerEntityType(instance, "TAINT_LIGHTNING", "taintlightning");
        registerEntityType(instance, "CULTIST_PORTAL_LESSER", "cultistportallesser");
        registerEntityType(instance, "CULTIST_PORTAL_GREATER", "cultistportalgreater");
        registerEntityType(instance, "SPECIAL_ITEM", "specialitem");
        registerEntityType(instance, "PERMANENT_ITEM", "permanentitem");
        registerEntityType(instance, "FOLLOW_ITEM", "followitem");
        registerEntityType(instance, "FALLING_TAINT", "fallingtaint");
        registerEntityType(instance, "ALUMENTUM", "alumentum");
        registerEntityType(instance, "PRIMAL_ORB", "primalorb");
        registerEntityType(instance, "FROST_SHARD", "frostshard");
        registerEntityType(instance, "PRIMAL_ARROW", "primalarrow");
        registerEntityType(instance, "GOLEM_DART", "golemdart");
        registerEntityType(instance, "PECH_BLAST", "pechblast");
        registerEntityType(instance, "ELDRITCH_ORB", "eldritchorb");
        registerEntityType(instance, "BOTTLE_OF_TAINT", "bottletaint");
        registerEntityType(instance, "GOLEM_ORB", "golemorb");
        registerEntityType(instance, "HOMING_SHARD", "homingshard");
        registerEntityType(instance, "SHOCK_ORB", "shockorb");
        registerEntityType(instance, "EXPLOSIVE_ORB", "explosiveorb");
        registerEntityType(instance, "EMBER", "ember");
        registerEntityType(instance, "GRAPPLE", "grapple");
        registerEntityType(instance, "TURRET_FOCUS", "turretfocus");
        registerEntityType(instance, "TURRET_ADVANCED", "turretadvanced");
        registerEntityType(instance, "TURRET_ELDRITCH", "turreteldritch");
        registerEntityType(instance, "NODE_MAGNET", "nodemagnet");
        registerEntityType(instance, "GOLEM", "golem");
        registerEntityType(instance, "ELDRITCH_WARDEN", "eldritchwarden");
        registerEntityType(instance, "ELDRITCH_GOLEM", "eldritchgolem");
        registerEntityType(instance, "CULTIST_LEADER", "cultistleader");
        registerEntityType(instance, "TAINTACLE_GIANT", "taintaclegiant");
        registerEntityType(instance, "BRAINY_ZOMBIE", "brainyzombie");
        registerEntityType(instance, "GIANT_BRAINY_ZOMBIE", "giantbrainyzombie");
        registerEntityType(instance, "WISP", "wisp");
        registerEntityType(instance, "FIREBAT", "firebat");
        registerEntityType(instance, "PECH", "pech");
        registerEntityType(instance, "MIND_SPIDER", "mindspider");
        registerEntityType(instance, "ELDRITCH_GUARDIAN", "eldritchguardian");
        registerEntityType(instance, "CULTIST_KNIGHT", "cultistknight");
        registerEntityType(instance, "CULTIST_CLERIC", "cultistcleric");
        registerEntityType(instance, "ELDRITCH_CRAB", "eldritchcrab");
        registerEntityType(instance, "INHABITED_ZOMBIE", "inhabitedzombie");
        registerEntityType(instance, "THAUM_SLIME", "thaumslime");
        registerEntityType(instance, "TAINTACLE", "taintacle");
        registerEntityType(instance, "TAINT_CRAWLER", "taintcrawler");
        registerEntityType(instance, "TAINTACLE_TINY", "taintacletiny");
        registerEntityType(instance, "TAINT_SWARM", "taintswarm");
        registerEntityType(instance, "TAINTED_CHICKEN", "taintedchicken");
        registerEntityType(instance, "TAINTED_RABBIT", "taintedrabbit");
        registerEntityType(instance, "TAINTED_COW", "taintedcow");
        registerEntityType(instance, "TAINTED_CREEPER", "taintedcreeper");
        registerEntityType(instance, "TAINTED_PIG", "taintedpig");
        registerEntityType(instance, "TAINTED_SHEEP", "taintedsheep");
        registerEntityType(instance, "TAINTED_VILLAGER", "taintedvillager");

    }

    private static void registerEntityType(EntityTypeRegistryModule module, String fieldName, String entityId) {
        RegistryHelper.setFinalStatic(ThaumicEntityTypes.class, fieldName, module.getById("thaumcraft:" + entityId).get());
    }

}
