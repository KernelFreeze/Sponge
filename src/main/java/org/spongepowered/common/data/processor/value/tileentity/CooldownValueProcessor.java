/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
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
package org.spongepowered.common.data.processor.value.tileentity;

import net.minecraft.tileentity.TileEntityHopper;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.common.data.processor.common.AbstractSpongeValueProcessor;
import org.spongepowered.common.data.value.SpongeValueFactory;

import java.util.Optional;

public class CooldownValueProcessor extends AbstractSpongeValueProcessor<TileEntityHopper, Integer, MutableBoundedValue<Integer>> {

    public CooldownValueProcessor() {
        super(TileEntityHopper.class, Keys.COOLDOWN);
    }

    @Override
    public MutableBoundedValue<Integer> constructValue(Integer value) {
        return SpongeValueFactory.boundedBuilder(Keys.COOLDOWN)
                .minimum(1)
                .maximum(Integer.MAX_VALUE)
                .defaultValue(8)
                .actualValue(value)
                .build();
    }

    @Override
    public boolean set(TileEntityHopper container, Integer value) {
        if (value < 1) {
            return false;
        }
        container.transferCooldown = value;
        return true;
    }

    @Override
    public Optional<Integer> getVal(TileEntityHopper container) {
        return Optional.ofNullable(container.transferCooldown < 1 ? null : container.transferCooldown);
    }

    @Override
    public ImmutableValue<Integer> constructImmutableValue(Integer value) {
        return constructValue(value).asImmutable();
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        if (container instanceof TileEntityHopper) {
            int cooldown = ((TileEntityHopper) container).transferCooldown;
            if (cooldown < 1) {
                return DataTransactionResult.failNoData();
            }
            ((TileEntityHopper) container).transferCooldown = -1;
            return DataTransactionResult.successRemove(constructImmutableValue(cooldown));
        }
        return DataTransactionResult.failNoData();
    }
}
