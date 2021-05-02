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
package org.spongepowered.common.command.brigadier;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import org.spongepowered.api.command.exception.ArgumentParseException;
import org.spongepowered.api.command.parameter.ArgumentReader;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;

public final class SpongeImmutableArgumentReader implements ArgumentReader.Immutable, ImmutableStringReader {

    private final String input;
    private final int cursor;
    private final int length;
    private final int remaining;

    SpongeImmutableArgumentReader(final String input, final int cursor) {
        this.input = input;
        this.cursor = cursor;
        this.length = this.input.length();
        this.remaining = this.length - cursor;
    }

    @Override
    public String getString() {
        return this.input;
    }

    @Override
    public int getRemainingLength() {
        return this.remainingLength();
    }

    @Override
    public int getTotalLength() {
        return this.totalLength();
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }

    @Override
    public String getRead() {
        return this.parsed();
    }

    @Override
    public String getRemaining() {
        return this.remaining();
    }

    @Override
    public @NonNull String input() {
        return this.input;
    }

    @Override
    public int remainingLength() {
        return this.remaining;
    }

    @Override
    public int totalLength() {
        return this.length;
    }

    @Override
    public int cursor() {
        return this.cursor;
    }

    @Override
    public @NonNull String parsed() {
        if (this.cursor == 0) {
            return "";
        }
        return this.input.substring(0, this.cursor);
    }

    @Override
    public @NonNull String remaining() {
        return this.input.substring(this.cursor);
    }

    @Override
    public boolean canRead(final int length) {
        return this.cursor + length <= this.length;
    }

    @Override
    public boolean canRead() {
        return this.canRead(1);
    }

    @Override
    public char peekCharacter() {
        if (this.canRead()) {
            return this.input.charAt(this.cursor);
        }
        throw new IllegalStateException("Cannot read past the end of a string!");
    }

    @Override
    public @NonNull ArgumentParseException createException(final @NonNull Component errorMessage) {
        return new ArgumentParseException(errorMessage, this.input(), this.cursor());
    }

    @Override
    public char peek() {
        return this.peek(0);
    }

    @Override
    public char peek(final int offset) {
        return this.input.charAt(this.cursor + offset);
    }

    @Override
    @SuppressWarnings("ConstantConditions") // suppressed due to ArgumentReader.Mutable being mixed into StringReader.
    public ArgumentReader.@NonNull Mutable mutable() {
        final StringReader reader = new StringReader(this.input);
        reader.setCursor(this.cursor);
        return (ArgumentReader.Mutable) reader;
    }

}
