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
package com.gabizou.thaumicsponge;

import static org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.JAVA_8;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class ThaumicSpongeCoreMod implements IFMLLoadingPlugin {

    static File modFile;

    public ThaumicSpongeCoreMod() {
        // Let's get this party started
        MixinBootstrap.init();
        MixinEnvironment.setCompatibilityLevel(JAVA_8);

        // Register common mixin configurations
        MixinEnvironment.getDefaultEnvironment()
                .addConfiguration("mixins.thaumicsponge.api.json")
                .addConfiguration("mixins.thaumicsponge.core.json")
                .addConfiguration("mixins.thaumicsponge.sponge.json");

    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        modFile = (File) data.get("coremodLocation");
        if (modFile == null) {
            modFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return "com.gabizou.thaumicsponge.ThaumicAccessTransformer";
    }

}
