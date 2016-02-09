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
package com.gabizou.thaumicsponge.data.processor.entity;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.entity.ImmutableWarpData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.entity.WarpData;
import com.gabizou.thaumicsponge.api.data.type.WarpType;
import com.gabizou.thaumicsponge.api.data.type.WarpTypes;
import com.gabizou.thaumicsponge.data.manipulator.mutable.entity.ThaumicWarpData;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.common.data.processor.common.AbstractSingleDataSingleTargetProcessor;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeMapValue;
import org.spongepowered.common.data.value.mutable.SpongeMapValue;
import thaumcraft.common.Thaumcraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerWarpDataProcessor extends AbstractSingleDataSingleTargetProcessor<EntityPlayer, Map<WarpType, Integer>, MapValue<WarpType, Integer>, WarpData, ImmutableWarpData> {

    public PlayerWarpDataProcessor() {
        super(ThaumicKeys.PLAYER_WARP, EntityPlayer.class);
    }

    @Override
    protected boolean set(EntityPlayer dataHolder, Map<WarpType, Integer> value) {
        int normal = value.get(WarpTypes.NORMAL);
        int temporary = value.get(WarpTypes.TEMPORARY);
        int permanent = value.get(WarpTypes.PERMANENT);
        Thaumcraft.proxy.getPlayerKnowledge().setWarpSticky(dataHolder.getName(), normal);
        Thaumcraft.proxy.getPlayerKnowledge().setWarpTemp(dataHolder.getName(), temporary);
        Thaumcraft.proxy.getPlayerKnowledge().setWarpPerm(dataHolder.getName(), permanent);
        Thaumcraft.proxy.getPlayerKnowledge().setWarpCounter(dataHolder.getName(), Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(dataHolder.getName()));
        return true;
    }

    @Override
    protected Optional<Map<WarpType, Integer>> getVal(EntityPlayer dataHolder) {
        Map<WarpType, Integer> map = new HashMap<>(3);
        map.put(WarpTypes.NORMAL, Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(dataHolder.getName()));
        map.put(WarpTypes.TEMPORARY, Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(dataHolder.getName()));
        map.put(WarpTypes.PERMANENT, Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(dataHolder.getName()));
        return Optional.of(map);
    }

    @Override
    protected ImmutableValue<Map<WarpType, Integer>> constructImmutableValue(Map<WarpType, Integer> value) {
        return new ImmutableSpongeMapValue<>(ThaumicKeys.PLAYER_WARP, value);
    }

    @Override
    protected MapValue<WarpType, Integer> constructValue(Map<WarpType, Integer> actualValue) {
        return new SpongeMapValue<>(ThaumicKeys.PLAYER_WARP, actualValue);
    }

    @Override
    protected WarpData createManipulator() {
        return new ThaumicWarpData();
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }
}
