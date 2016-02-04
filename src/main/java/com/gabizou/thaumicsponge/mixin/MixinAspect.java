package com.gabizou.thaumicsponge.mixin;

import com.gabizou.thaumicsponge.api.ThaumicAspect;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.api.text.translation.Translation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import thaumcraft.api.aspects.Aspect;

import java.util.Collection;

@Mixin(Aspect.class)
public abstract class MixinAspect implements ThaumicAspect {

    @Shadow String tag;
    @Shadow Aspect[] components;
    @Shadow int color;
    @Shadow private String chatcolor;
    @Shadow ResourceLocation image;
    @Shadow int blend;
    @Shadow public abstract boolean isPrimal();

    @Override
    public Collection<ThaumicAspect> getComponents() {
        return ImmutableList.copyOf((ThaumicAspect[]) this.components);
    }

    @Override
    public boolean isPrimalAspect() {
        return this.isPrimal();
    }

    @Override
    public String getId() {
        return this.tag;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Translation getTranslation() {
        return null;
    }
}
