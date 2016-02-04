package com.gabizou.thaumicsponge.mixin.entity;

import com.gabizou.thaumicsponge.api.entity.EldritchMonster;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.api.entities.IEldritchMob;

@Mixin(IEldritchMob.class)
public interface MixinIEldritchMob extends EldritchMonster {

}
