package com.gabizou.thaumicsponge;

import net.minecraftforge.fml.common.DummyModContainer;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Optional;

public class ThaumicSpongeMod extends DummyModContainer implements PluginContainer{

    @Override
    public String getId() {
        return "thaumicsponge";
    }

    @Override
    public Optional<Object> getInstance() {
        return Optional.of(this);
    }
}
