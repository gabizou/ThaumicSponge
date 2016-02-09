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
package com.gabizou.thaumicsponge.data.manipulator.mutable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.ImmutableAuraNodeData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.data.manipulator.immutable.ImmutableThaumicAuraNodeData;
import com.google.common.collect.ComparisonChain;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.common.data.value.SpongeValueFactory;
import org.spongepowered.common.data.value.mutable.SpongeValue;

public class ThaumicAuraNodeData extends AbstractData<AuraNodeData, ImmutableAuraNodeData> implements AuraNodeData{

    private int nodeSize;
    private Aspect aspect;
    private boolean stable;
    private AuraNodeType nodeType;

    public ThaumicAuraNodeData() {
        this(1, Aspects.AER, true, AuraNodeTypes.NORMAL);
    }

    public ThaumicAuraNodeData(int nodeSize, Aspect aspect, boolean stable, AuraNodeType nodeType) {
        super(AuraNodeData.class);
        this.nodeSize = nodeSize;
        this.aspect = aspect;
        this.stable = stable;
        this.nodeType = nodeType;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(ThaumicKeys.AURA_NODE_ASPECT, () -> this.aspect);
        registerFieldSetter(ThaumicKeys.AURA_NODE_ASPECT, aspect -> this.aspect = checkNotNull(aspect, "Aspect cannot be null!"));
        registerKeyValue(ThaumicKeys.AURA_NODE_ASPECT, this::aspect);

        registerFieldGetter(ThaumicKeys.AURA_NODE_IS_STABLE, () -> this.stable);
        registerFieldSetter(ThaumicKeys.AURA_NODE_IS_STABLE, stable -> this.stable = stable);
        registerKeyValue(ThaumicKeys.AURA_NODE_IS_STABLE, this::stabilized);

        registerFieldGetter(ThaumicKeys.AURA_NODE_TYPE, () -> this.nodeType);
        registerFieldSetter(ThaumicKeys.AURA_NODE_TYPE, nodeType -> this.nodeType = checkNotNull(nodeType, "Node type cannot be null!"));
        registerKeyValue(ThaumicKeys.AURA_NODE_TYPE, this::nodeType);

        registerFieldGetter(ThaumicKeys.AURA_NODE_SIZE, () -> this.nodeSize);
        registerFieldSetter(ThaumicKeys.AURA_NODE_SIZE, size -> {
            checkArgument(size >= 0, "Size must be greater than or equal to zero!");
            this.nodeSize = size;
        });
        registerKeyValue(ThaumicKeys.AURA_NODE_SIZE, this::nodeSize);
    }

    @Override
    public AuraNodeData copy() {
        return new ThaumicAuraNodeData(this.nodeSize, this.aspect, this.stable, this.nodeType);
    }

    @Override
    public ImmutableAuraNodeData asImmutable() {
        return new ImmutableThaumicAuraNodeData(this.nodeSize, this.aspect, this.stable, this.nodeType);
    }

    @Override
    public int compareTo(AuraNodeData o) {
        return ComparisonChain.start()
                .compare(o.aspect().get().getId(), this.aspect.getId())
                .compare(o.nodeSize().get().intValue(), this.nodeSize)
                .compare(o.nodeType().get().getId(), this.nodeType.getId())
                .compare(o.stabilized().get(), this.stable)
                .result();
    }

    @Override
    public Value<Boolean> stabilized() {
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_IS_STABLE, true, this.stable);
    }

    @Override
    public Value<AuraNodeType> nodeType() {
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_TYPE, AuraNodeTypes.NORMAL, this.nodeType);
    }

    @Override
    public Value<Aspect> aspect() {
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_ASPECT, Aspects.AER, this.aspect);
    }

    @Override
    public MutableBoundedValue<Integer> nodeSize() {
        return SpongeValueFactory.boundedBuilder(ThaumicKeys.AURA_NODE_SIZE)
                .defaultValue(1)
                .minimum(0)
                .maximum(Integer.MAX_VALUE)
                .actualValue(this.nodeSize)
                .build();
    }
}
