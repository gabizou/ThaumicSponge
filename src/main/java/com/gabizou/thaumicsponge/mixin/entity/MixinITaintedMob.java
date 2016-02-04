package com.gabizou.thaumicsponge.mixin.entity;

import com.gabizou.thaumicsponge.api.entity.Tainted;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.api.entities.ITaintedMob;

@Mixin(ITaintedMob.class)
public interface MixinITaintedMob extends Tainted {

}
