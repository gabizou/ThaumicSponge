package com.gabizou.thaumicsponge;

import static org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.JAVA_8;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class ThaumicSpongeCoreMod implements IFMLLoadingPlugin {

    private File modFile;

    public ThaumicSpongeCoreMod() {
        // Let's get this party started
        MixinBootstrap.init();
        MixinEnvironment.setCompatibilityLevel(JAVA_8);

        // Register common mixin configurations
        MixinEnvironment.getDefaultEnvironment()
                .addConfiguration("mixins.thaumicsponge.api.json")
                .addConfiguration("mixins.thaumicsponge.core.json");

    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {};
    }

    @Override
    public String getModContainerClass() {
        return "com.gabizou.thaumicsponge.ThaumicSpongeMod";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        this.modFile = (File) data.get("coremodLocation");
        if (this.modFile == null) {
            this.modFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
