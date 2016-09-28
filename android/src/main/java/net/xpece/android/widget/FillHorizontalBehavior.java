/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The {@link Behavior} for a view that is positioned horizontally next to another view.
 */
public abstract class FillHorizontalBehavior extends Behavior<View> {

    public FillHorizontalBehavior() {}

    @Keep
    public FillHorizontalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child,
                                  int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec,
                                  int heightUsed) {
        final int childLpWidth = child.getLayoutParams().width;
        if (childLpWidth == ViewGroup.LayoutParams.MATCH_PARENT
                || childLpWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            // If the menu's height is set to match_parent/wrap_content then measure it
            // with the maximum visible height

            final List<View> dependencies = parent.getDependencies(child);
            final View header = findFirstDependency(dependencies);
            if (header != null) {
                if (ViewCompat.getFitsSystemWindows(header)
                        && !ViewCompat.getFitsSystemWindows(child)) {
                    // If the header is fitting system windows then we need to also,
                    // otherwise we'll get CoL's compatible measuring
                    ViewCompat.setFitsSystemWindows(child, true);

                    if (ViewCompat.getFitsSystemWindows(child)) {
                        // If the set succeeded, trigger a new layout and return true
                        child.requestLayout();
                        return true;
                    }
                }

                int availableWidth = View.MeasureSpec.getSize(parentWidthMeasureSpec);
                if (availableWidth == 0) {
                    // If the measure spec doesn't specify a size, use the current width
                    availableWidth = parent.getWidth();
                }

                final int width = availableWidth - header.getMeasuredWidth();
                final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                        childLpWidth == ViewGroup.LayoutParams.MATCH_PARENT
                                ? View.MeasureSpec.EXACTLY
                                : View.MeasureSpec.AT_MOST);

                // Now measure the scrolling view with the correct width
                parent.onMeasureChild(child, widthMeasureSpec,
                        widthUsed, parentHeightMeasureSpec, heightUsed);

                return true;
            }
        }
        return false;
    }

    abstract View findFirstDependency(List<View> views);
}
