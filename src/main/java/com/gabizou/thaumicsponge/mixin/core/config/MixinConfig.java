/**
 * This file is part of ThaumicSponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) Gabriel Harris-Rouquette
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
