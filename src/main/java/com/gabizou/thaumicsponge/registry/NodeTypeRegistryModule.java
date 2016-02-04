package com.gabizou.thaumicsponge.registry;

import static com.google.common.base.Preconditions.checkNotNull;

import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.interfaces.IMixinNodeType;
import com.google.common.collect.ImmutableSet;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import thaumcraft.common.lib.aura.NodeType;

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
        ((IMixinNodeType) nodeType).setNodeTypeId(id);
        this.nodeTypeMap.put(id, (AuraNodeType) nodeType);
    }
}
