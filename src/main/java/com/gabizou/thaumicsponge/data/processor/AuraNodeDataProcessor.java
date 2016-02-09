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
package com.gabizou.thaumicsponge.data.processor;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.ImmutableAuraNodeData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.data.manipulator.mutable.ThaumicAuraNodeData;
import com.gabizou.thaumicsponge.mixin.interfaces.IMixinAura;
import com.gabizou.thaumicsponge.mixin.interfaces.IMixinNodeType;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.common.data.processor.common.AbstractMultiDataSingleTargetProcessor;
import thaumcraft.common.lib.aura.EntityAuraNode;
import thaumcraft.common.lib.aura.NodeType;

import java.util.Map;
import java.util.Optional;

public class AuraNodeDataProcessor extends AbstractMultiDataSingleTargetProcessor<EntityAuraNode, AuraNodeData, ImmutableAuraNodeData> {

    public AuraNodeDataProcessor() {
        super(EntityAuraNode.class);
    }

    @Override
    protected boolean doesDataExist(EntityAuraNode dataHolder) {
        return true;
    }

    @Override
    protected boolean set(EntityAuraNode dataHolder, Map<Key<?>, Object> keyValues) {
        final int amount = (int) keyValues.get(ThaumicKeys.AURA_NODE_SIZE);
        final Aspect aspect = (Aspect) keyValues.get(ThaumicKeys.AURA_NODE_ASPECT);
        final boolean stable = (boolean) keyValues.get(ThaumicKeys.AURA_NODE_IS_STABLE);
        final AuraNodeType nodeType = (AuraNodeType) keyValues.get(ThaumicKeys.AURA_NODE_TYPE);
        dataHolder.setNodeSize(amount);
        dataHolder.setNodeType(((IMixinNodeType) nodeType).getNodeType());
        dataHolder.setAspectTag(aspect.getId());
        ((IMixinAura) dataHolder).setStabilized(stable);
        return true;
    }

    @Override
    protected Map<Key<?>, ?> getValues(EntityAuraNode dataHolder) {
        ImmutableMap.Builder<Key<?>, Object> builder = ImmutableMap.builder();
        thaumcraft.api.aspects.Aspect aspect = dataHolder.getAspect();
        builder.put(ThaumicKeys.AURA_NODE_ASPECT, aspect == null ? Aspects.AER : aspect);
        builder.put(ThaumicKeys.AURA_NODE_IS_STABLE, ((IMixinAura) dataHolder).isStable());
        builder.put(ThaumicKeys.AURA_NODE_SIZE, dataHolder.getNodeSize());
        builder.put(ThaumicKeys.AURA_NODE_TYPE, NodeType.nodeTypes[dataHolder.getNodeType()]);
        return builder.build();
    }

    @Override
    protected AuraNodeData createManipulator() {
        return new ThaumicAuraNodeData();
    }

    @Override
    public Optional<AuraNodeData> fill(DataContainer container, AuraNodeData auraNodeData) {
        return Optional.empty();
    }

    @Override
    public DataTransactionResult remove(DataHolder dataHolder) {
        return DataTransactionResult.failNoData();
    }
}
