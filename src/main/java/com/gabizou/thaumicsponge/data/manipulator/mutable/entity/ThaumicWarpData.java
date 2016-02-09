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
package com.gabizou.thaumicsponge.data.manipulator.mutable.entity;

import com.gabizou.thaumicsponge.api.data.ThaumicKeys;
import com.gabizou.thaumicsponge.api.data.manipulator.immutable.entity.ImmutableWarpData;
import com.gabizou.thaumicsponge.api.data.manipulator.mutable.entity.WarpData;
import com.gabizou.thaumicsponge.api.data.type.WarpType;
import com.gabizou.thaumicsponge.data.manipulator.immutable.entity.ImmutableThaumicWarpData;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.common.data.manipulator.mutable.common.collection.AbstractSingleMapData;

import java.util.HashMap;
import java.util.Map;

public class ThaumicWarpData extends AbstractSingleMapData<WarpType, Integer, WarpData, ImmutableWarpData> implements WarpData {

    public ThaumicWarpData() {
        this(new HashMap<>(3));
    }

    public ThaumicWarpData(Map<WarpType, Integer> value) {
        super(WarpData.class, value, ThaumicKeys.PLAYER_WARP, ImmutableThaumicWarpData.class);
    }

    @Override
    public int compareTo(WarpData o) {
        return 0;
    }

    @Override
    public MapValue<WarpType, Integer> warp() {
        return getValueGetter();
    }
}
