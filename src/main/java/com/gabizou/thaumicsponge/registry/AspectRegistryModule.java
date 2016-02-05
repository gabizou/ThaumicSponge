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
package com.gabizou.thaumicsponge.registry;

import static com.google.common.base.Preconditions.checkNotNull;

import com.gabizou.thaumicsponge.ReflectionUtil;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.google.common.collect.ImmutableSet;
import org.spongepowered.api.registry.util.CustomCatalogRegistration;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.common.registry.SpongeAdditionalCatalogRegistryModule;

import java.lang.reflect.Field;
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
            thaumcraft.api.aspects.Aspect aspect = thaumcraft.api.aspects.Aspect.AIR;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @CustomCatalogRegistration
    public void registerCatalog() {
        thaumcraft.api.aspects.Aspect dummyForclassloading = thaumcraft.api.aspects.Aspect.AIR;
        for (Field field : Aspects.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Aspect aspect = this.aspectMap.get(field.getName().toLowerCase());
                if (aspect != null) {
                    ReflectionUtil.setStaticFinalField(field, aspect);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final class Holder {
        static final AspectRegistryModule INSTANCE = new AspectRegistryModule();
    }
}
