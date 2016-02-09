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
package com.gabizou.thaumicsponge.mixin.core.entity;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.api.entity.AuraNode;
import com.gabizou.thaumicsponge.data.manipulator.mutable.ThaumicAuraNodeData;
import com.gabizou.thaumicsponge.mixin.interfaces.IMixinAura;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.data.value.SpongeValueFactory;
import org.spongepowered.common.data.value.mutable.SpongeValue;
import org.spongepowered.common.mixin.core.entity.MixinEntity;
import thaumcraft.common.lib.aura.EntityAuraNode;
import thaumcraft.common.lib.aura.NodeType;

import java.util.List;

import javax.annotation.Nullable;

@Mixin(EntityAuraNode.class)
public abstract class MixinEntityAura extends MixinEntity implements AuraNode, IMixinAura {

    @Shadow(remap = false) private boolean stablized;

    @Shadow(remap = false) public abstract int getNodeType();
    @Shadow(remap = false)
    @Nullable
    public abstract thaumcraft.api.aspects.Aspect getAspect();
    @Shadow(remap = false) public abstract int getNodeSize();

    @Override
    public AuraNodeData auraNodeData() {
        Aspect aspect = (Aspect) this.getAspect();
        return new ThaumicAuraNodeData(this.getNodeSize(), aspect == null ? Aspects.AER : aspect, this.stablized, (AuraNodeType) NodeType.nodeTypes[this.getNodeType()]);
    }

    @Override
    public Value<Boolean> stabilized() {
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_IS_STABLE, true, this.stablized);
    }

    @Override
    public Value<AuraNodeType> nodeType() {
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_TYPE, AuraNodeTypes.NORMAL, (AuraNodeType) NodeType.nodeTypes[this.getNodeType()]);
    }

    @Override
    public Value<Aspect> aspect() {
        Aspect aspect = (Aspect) this.getAspect();
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_ASPECT, Aspects.AER, aspect == null ? Aspects.AER : aspect);
    }

    @Override
    public MutableBoundedValue<Integer> nodeSize() {
        return SpongeValueFactory.boundedBuilder(ThaumicKeys.AURA_NODE_SIZE)
                .defaultValue(1)
                .minimum(0)
                .maximum(Integer.MAX_VALUE)
                .actualValue(this.getNodeSize())
                .build();
    }

    @Override
    public boolean isStable() {
        return this.stablized;
    }

    @Override
    public void setStabilized(boolean stabilized) {
        this.stablized = stabilized;
    }

    @Override
    public void supplyVanillaManipulators(List<DataManipulator<?, ?>> manipulators) {
        super.supplyVanillaManipulators(manipulators);
        manipulators.add(auraNodeData());
    }
}
