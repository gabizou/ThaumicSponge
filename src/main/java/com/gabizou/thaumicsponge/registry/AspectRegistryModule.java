package com.gabizou.thaumicsponge.registry;

import static com.google.common.base.Preconditions.checkNotNull;

import com.gabizou.thaumicsponge.api.Aspect;
import com.gabizou.thaumicsponge.api.Aspects;
import com.google.common.collect.ImmutableSet;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.common.registry.SpongeAdditionalCatalogRegistryModule;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class AspectRegistryModule implements SpongeAdditionalCatalogRegistryModule<Aspect> {

    public static AspectRegistryModule getInstance() {
        return Holder.INSTANCE;
    }

    @RegisterCatalog(Aspects.class)
    private final Map<String, Aspect> aspectMap = new LinkedHashMap<>();

    public void registerAspect(Aspect aspect, Object name) {
        this.aspectMap.put(aspect.getId(), aspect);
    }
    @Override
    public void registerAdditionalCatalog(Aspect extraCatalog) {
        this.aspectMap.put(extraCatalog.getId(), extraCatalog);
    }

    @Override
    public Optional<Aspect> getById(String id) {
        return Optional.ofNullable(this.aspectMap.get(checkNotNull(id, "Id cannot be null!").toLowerCase()));
    }

    @Override
    public Collection<Aspect> getAll() {
        return ImmutableSet.copyOf(this.aspectMap.values());
    }

    @Override
    public boolean allowsApiRegistration() {
        return false;
    }

    private AspectRegistryModule() {
    }

    @Override
    public void registerDefaults() {
        try {
            // We just need to initialize these
            Class<?> aspectClass = Class.forName("thaumcraft.api.aspects.Aspect");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final class Holder {
        static final AspectRegistryModule INSTANCE = new AspectRegistryModule();
    }
}
