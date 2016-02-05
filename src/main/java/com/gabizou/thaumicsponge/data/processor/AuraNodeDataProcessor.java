package com.gabizou.thaumicsponge.data.processor;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.ImmutableAuraNodeData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspect;
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
        builder.put(ThaumicKeys.AURA_NODE_ASPECT, dataHolder.getAspect());
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
