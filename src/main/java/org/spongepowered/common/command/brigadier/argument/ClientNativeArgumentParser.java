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
package org.spongepowered.common.command.brigadier.argument;

import org.checkerframework.checker.nullness.qual.NonNull;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.command.parameter.managed.standard.ResourceKeyedValueParameter;

import com.mojang.brigadier.arguments.ArgumentType;

/**
 * For use with ArgumentTypes in the base game
 */
public final class ClientNativeArgumentParser<S, T> extends StandardArgumentParser<S, T> implements ResourceKeyedValueParameter<T> {

    public static <T> ClientNativeArgumentParser<T, T> createIdentity(final ResourceKey key, final ArgumentType<T> type) {
        return new ClientNativeArgumentParser<>(key, type, (reader, c, x) -> x);
    }

    public static <S, T> ClientNativeArgumentParser<S, T> createConverter(
            final ResourceKey key,
            final ArgumentType<S> type,
            final StandardArgumentParser.Converter<S, T> converter) {
        return new ClientNativeArgumentParser<>(key, type, converter);
    }

    // ---

    private final ResourceKey key;

    private ClientNativeArgumentParser(
            final ResourceKey key,
            final ArgumentType<S> type,
            final StandardArgumentParser.Converter<S, T> converter) {
        super(type, converter);
        this.key = key;
    }

    @Override
    public @NonNull ResourceKey key() {
        return this.key;
    }

    @Override
    public boolean hasClientNativeCompletions() {
        return true;
    }

}
