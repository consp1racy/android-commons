/*
 * Copyright 2014 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.xpece.android.text;

import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

/**
 * A {@link SpannableStringBuilder} wrapper whose API doesn't make me want to stab my eyes out.
 */
@SuppressWarnings("unused")
public class Truss {

    private final SpannableStringBuilder builder = new SpannableStringBuilder();
    private final Deque<Span> stack = new ArrayDeque<>();

    @NonNull
    public Truss append(@NonNull String string) {
        builder.append(string);
        return this;
    }

    @NonNull
    public Truss append(@NonNull CharSequence charSequence) {
        builder.append(charSequence);
        return this;
    }

    @NonNull
    public Truss append(char c) {
        builder.append(c);
        return this;
    }

    @NonNull
    public Truss append(int number) {
        builder.append(String.valueOf(number));
        return this;
    }

    /**
     * Starts {@code span} at the current position in the builder.
     */
    @NonNull
    public Truss pushSpan(@NonNull Object span) {
        stack.addLast(new Span(builder.length(), span));
        return this;
    }

    /**
     * End the most recently pushed span at the current position in the builder.
     */
    @NonNull
    public Truss popSpan() {
        Span span = stack.removeLast();
        builder.setSpan(span.span, span.start, builder.length(), SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * Create the final {@link CharSequence}, popping any remaining spans.
     */
    @NonNull
    public CharSequence build() {
        while (!stack.isEmpty()) {
            popSpan();
        }
        return builder; // TODO make immutable copy?
    }

    private static final class Span {

        final int start;
        final Object span;

        public Span(int start, @NonNull Object span) {
            this.start = start;
            this.span = span;
        }
    }
}
