package com.gabizou.thaumicsponge.mixin.core.config;

import com.gabizou.thaumicsponge.api.effect.ThaumicPotionEffectTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.common.registry.RegistryHelper;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.api.potions.PotionVisExhaust;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.potions.PotionBlurredVision;
import thaumcraft.common.lib.potions.PotionDeathGaze;
import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
import thaumcraft.common.lib.potions.PotionSunScorned;
import thaumcraft.common.lib.potions.PotionThaumarhia;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;
import thaumcraft.common.lib.potions.PotionWarpWard;

@Mixin(Config.class)
public class MixinConfig {

    @Inject(method = "initPotions", at = @At("HEAD"), remap = false)
    private static void onInitPotions(CallbackInfo callbackInfo) {
        try {
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "FLUX", PotionFluxTaint.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "VIS_EXHAUST", PotionVisExhaust.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "INFECTIOUS_VIS_EXHAUST", PotionInfectiousVisExhaust.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "UNNATURAL_HUNGER", PotionUnnaturalHunger.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "WARP_WARD", PotionWarpWard.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "DEATH_GAZE", PotionDeathGaze.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "BLURRED_VISION", PotionBlurredVision.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "SUN_SCORNED", PotionSunScorned.instance);
            RegistryHelper.setFinalStatic(ThaumicPotionEffectTypes.class, "THAUMARHIA", PotionThaumarhia.instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
