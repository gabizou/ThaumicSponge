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
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.mixin.interfaces.IMixinNodeType;
import com.google.common.collect.ImmutableSet;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.util.CustomCatalogRegistration;
import org.spongepowered.api.registry.util.RegisterCatalog;
import thaumcraft.common.lib.aura.NodeType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class NodeTypeRegistryModule implements CatalogRegistryModule<AuraNodeType> {

    @RegisterCatalog(AuraNodeTypes.class)
    private final Map<String, AuraNodeType> nodeTypeMap = new LinkedHashMap<>(7);

    @Override
    public Optional<AuraNodeType> getById(String id) {
        return Optional.ofNullable(this.nodeTypeMap.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<AuraNodeType> getAll() {
        return ImmutableSet.copyOf(this.nodeTypeMap.values());
    }

    @Override
    public void registerDefaults() {
        registerNode(NodeType.nodeTypes[0], "normal");
        registerNode(NodeType.nodeTypes[1], "dark");
        registerNode(NodeType.nodeTypes[2], "hungry");
        registerNode(NodeType.nodeTypes[3], "pure");
        registerNode(NodeType.nodeTypes[4], "taint");
        registerNode(NodeType.nodeTypes[5], "unstable");
        registerNode(NodeType.nodeTypes[6], "astral");
    }

    private void registerNode(NodeType nodeType, String id) {
        ((IMixinNodeType) nodeType).setNodeTypeId(id.toLowerCase());
        this.nodeTypeMap.put(id.toLowerCase(), (AuraNodeType) nodeType);
    }
}
