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

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.ImmutableAuraNodeData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.AuraNodeData;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.api.entity.AuraNode;
import com.gabizou.thaumicsponge.data.manipulator.immutable.ImmutableThaumicAuraNodeData;
import com.gabizou.thaumicsponge.data.manipulator.mutable.ThaumicAuraNodeData;
import com.gabizou.thaumicsponge.data.processor.AuraNodeDataProcessor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.common.data.SpongeDataManager;

@Plugin(id = "thaumicsponge", name = "ThaumicSponge", version = "0.0.1-SNAPSHOT")
public class ThaumicSponge {


    @Listener
    public void onInit(GamePreInitializationEvent event) {
        System.err.printf("Staring up ThaumicSponge!\n");
    }

    @Listener
    public void onPostInit(GameInitializationEvent event) {
        SpongeDataManager manager = SpongeDataManager.getInstance();
        manager.registerDataProcessorAndImpl(AuraNodeData.class, ThaumicAuraNodeData.class, ImmutableAuraNodeData.class,
                ImmutableThaumicAuraNodeData.class, new AuraNodeDataProcessor());
    }

    @Listener
    public void spawn(SpawnEntityEvent entityEvent) {
        for (Entity entity : entityEvent.getEntities()) {
            if (entity instanceof AuraNode) {
                AuraNodeData data = entity.get(AuraNodeData.class).get();
                System.err.printf("***** Found Aura Node *****\n");
                System.err.printf("Aura node size: " + data.nodeSize() + "\n");
                System.err.printf("Aura node type: " + data.nodeType().get().getId() + "\n");
                System.err.printf("Aura node Aspect: " + data.aspect().get().getId() + "\n");
                System.err.printf("Aura node stable: " + (data.stabilized().get() ? "true" : "false") + "\n");
                data.set(ThaumicKeys.AURA_NODE_ASPECT, Aspects.ALIENIS);
                data.set(ThaumicKeys.AURA_NODE_SIZE, 360);
                data.set(ThaumicKeys.AURA_NODE_TYPE, AuraNodeTypes.TAINT);
                entity.offer(data);
            }
        }
    }

}
