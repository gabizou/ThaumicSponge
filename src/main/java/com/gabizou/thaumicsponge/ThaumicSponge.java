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
import com.gabizou.thaumicsponge.api.data.type.Aspect;
import com.gabizou.thaumicsponge.api.data.type.Aspects;
import com.gabizou.thaumicsponge.api.data.type.AuraNodeTypes;
import com.gabizou.thaumicsponge.api.entity.AuraNode;
import com.gabizou.thaumicsponge.api.entity.ThaumicEntityTypes;
import com.gabizou.thaumicsponge.data.manipulator.immutable.ImmutableThaumicAuraNodeData;
import com.gabizou.thaumicsponge.data.manipulator.mutable.ThaumicAuraNodeData;
import com.gabizou.thaumicsponge.data.processor.AuraNodeDataProcessor;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.world.World;
import org.spongepowered.common.data.SpongeDataManager;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Plugin(id = "thaumicsponge", name = "ThaumicSponge", version = "0.0.1-SNAPSHOT")
public class ThaumicSponge {

    private final Map<UUID, UUID> auraMap = new ConcurrentHashMap<>();


    private static final Random RANDOM = new Random();

    @Listener
    public void onInit(GamePreInitializationEvent event) {
        System.err.printf("Staring up ThaumicSponge!\n");
    }

    @Listener
    public void onPostInit(GameInitializationEvent event) {
        SpongeDataManager manager = SpongeDataManager.getInstance();
        manager.registerDataProcessorAndImpl(AuraNodeData.class, ThaumicAuraNodeData.class, ImmutableAuraNodeData.class,
                ImmutableThaumicAuraNodeData.class, new AuraNodeDataProcessor());
        CommandManager commandManager = Sponge.getCommandManager();
        CommandSpec discoAuraCmd = CommandSpec.builder()
                .arguments(GenericArguments.none())
                .description(Text.of("Creates a new Aura node and turns it into a disco ball!"))
                .executor((executor, args) -> {
                    if (executor instanceof Player) {
                        Entity auraNode = ((Player) executor).getLocation().getExtent().createEntity(ThaumicEntityTypes.AURA_NODE, ((Player) executor).getLocation().getPosition()).get();
                        AuraNodeData nodeData = new ThaumicAuraNodeData(50, Aspects.AER, false, AuraNodeTypes.NORMAL);
                        auraNode.offer(nodeData);

                        auraNode.getWorld().spawnEntity(auraNode, Cause.of(NamedCause.source(this)));
                        this.auraMap.put(auraNode.getWorld().getUniqueId(), auraNode.getUniqueId());
                        return CommandResult.success();
                    }
                    return CommandResult.empty();
                })
                .build();
        commandManager.register(this, discoAuraCmd, "disco");
    }

    @Listener
    public void onServerSTarted(GameStartedServerEvent event) {
        Task.builder()
                .delay(5, TimeUnit.SECONDS)
                .interval(1, TimeUnit.SECONDS)
                .execute(() -> {
                    // DISCO DISCO TIME
                    ImmutableList<Aspect> aspects = ImmutableList.copyOf(Sponge.getRegistry().getAllOf(Aspect.class));
                    for (Map.Entry<UUID, UUID> entry : this.auraMap.entrySet()) {
                        Optional<World> world = Sponge.getServer().getWorld(entry.getKey());
                        world.ifPresent(spongeWorld -> {
                            Optional<Entity> aura = spongeWorld.getEntity(entry.getValue());
                            aura.ifPresent(auraNode -> {
                                AuraNodeData nodeData = ((AuraNode) auraNode).auraNodeData();
                                Aspect value = aspects.get(RANDOM.nextInt(aspects.size()));
                                nodeData.set(ThaumicKeys.AURA_NODE_ASPECT, value);
                                nodeData.set(ThaumicKeys.AURA_NODE_SIZE, RANDOM.nextInt(400));
                                auraNode.offer(nodeData);
                            });
                        });
                    }

                })
                .submit(this);
    }

}
