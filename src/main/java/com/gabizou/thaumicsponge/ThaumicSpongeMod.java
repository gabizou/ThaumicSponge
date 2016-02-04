package com.gabizou.thaumicsponge;

import com.gabizou.thaumicsponge.api.Aspect;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeType;
import com.gabizou.thaumicsponge.registry.AspectRegistryModule;
import com.gabizou.thaumicsponge.registry.NodeTypeRegistryModule;
import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.ModMetadata;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.common.SpongeImpl;
import thaumcraft.common.lib.aura.NodeType;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class ThaumicSpongeMod extends DummyModContainer implements PluginContainer {

    private File modFile;

    public ThaumicSpongeMod() {
        super(ThaumicSpongeMod.createMetadata(ImmutableMap.<String, Object>of("modid", SpongeImpl.ECOSYSTEM_ID, "name", "SpongeForge", "version", "DEV")));
        this.modFile = ThaumicSpongeCoreMod.modFile;

        SpongeImpl.getRegistry()
                .registerModule(AuraNodeType.class, new NodeTypeRegistryModule())
                .registerModule(Aspect.class, AspectRegistryModule.getInstance());
    }


    @Override
    public Object getMod() {
        return this;
    }

    @Override
    public File getSource() {
        return this.modFile;
    }

    @Override
    public Class<?> getCustomResourcePackClass() {
        if (getSource().isDirectory()) {
            return FMLFolderResourcePack.class;
        } else {
            return FMLFileResourcePack.class;
        }
    }

    @Override
    public String getId() {
        return getModId();
    }

    @Override
    public Optional<Object> getInstance() {
        return Optional.of(this);
    }

    private static ModMetadata createMetadata(Map<String, Object> defaults) {
        try {
            return MetadataCollection.from(ThaumicSpongeMod.class.getResourceAsStream("/mcmod.info"), SpongeImpl.ECOSYSTEM_ID).getMetadataForId(
                    SpongeImpl.ECOSYSTEM_ID, defaults);
        } catch (Exception ex) {
            return new ModMetadata();
        }
    }
}
