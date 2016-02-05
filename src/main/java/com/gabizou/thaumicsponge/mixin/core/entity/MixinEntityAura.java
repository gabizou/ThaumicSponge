package com.gabizou.thaumicsponge.mixin.core.entity;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.api.entity.AuraNode;
import com.gabizou.thaumicsponge.mixin.interfaces.IMixinAura;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.data.value.SpongeValueFactory;
import org.spongepowered.common.data.value.mutable.SpongeValue;
import thaumcraft.common.lib.aura.EntityAuraNode;
import thaumcraft.common.lib.aura.NodeType;

@Mixin(EntityAuraNode.class)
public abstract class MixinEntityAura extends Entity implements AuraNode, IMixinAura {

    @Shadow(remap = false) private boolean stablized;

    @Shadow(remap = false) public abstract int getNodeType();
    @Shadow(remap = false) public abstract thaumcraft.api.aspects.Aspect getAspect();
    @Shadow(remap = false) public abstract int getNodeSize();

    // ignored. this is a mixin
    public MixinEntityAura(World worldIn) {
        super(worldIn);
    }

    @Override
    public AuraNodeData auraNodeData() {
        return null;
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
        return new SpongeValue<>(ThaumicKeys.AURA_NODE_ASPECT, Aspects.AER, (Aspect) this.getAspect());
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
}
