package com.gabizou.thaumicsponge.mixin.aspect;

import com.gabizou.thaumicsponge.api.Aspect;
import com.gabizou.thaumicsponge.registry.AspectRegistryModule;
import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Mixin(thaumcraft.api.aspects.Aspect.class)
public abstract class MixinAspect implements Aspect {

    @Shadow String tag;
    @Shadow thaumcraft.api.aspects.Aspect[] components;

    @Shadow public abstract boolean isPrimal();

    @Override
    public Collection<Aspect> getComponents() {
        return ImmutableList.copyOf((Aspect[]) this.components);
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
        return this.tag;
    }

    /**
     * @author gabizou - February 4th, 2016
     *
     * This redirects the registration of an Aspect so that
     * the AspectRegistryModule will know of them.
     */
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/LinkedHashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object onRegister(LinkedHashMap<Object, Object> aspects, Object name, Object aspect) {
        AspectRegistryModule.getInstance().registerAspect((Aspect) aspect, name);
        System.err.printf("Registering Aspect " + name + "\n");
        aspects.put(name, aspect);
        return aspect;
    }
}
