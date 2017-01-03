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

package android.support.design.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

@RequiresApi(14)
@TargetApi(14)
class CardButtonIcs extends CardButtonGingerbread {

    private float mRotation;

    CardButtonIcs(Button view, CardButtonDelegate shadowViewDelegate, ValueAnimatorCompat.Creator animatorCreator) {
        super(view, shadowViewDelegate, animatorCreator);
        mRotation = mView.getRotation();
    }

    @Override
    boolean requirePreDrawListener() {
        return true;
    }

    @Override
    void onPreDraw() {
        final float rotation = mView.getRotation();
        if (mRotation != rotation) {
            mRotation = rotation;
            updateFromViewRotation();
        }
    }

    private void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            // KitKat seems to have an issue with views which are rotated with angles which are
            // not divisible by 90. Worked around by moving to software rendering in these cases.
            if ((mRotation % 90) != 0) {
                if (mView.getLayerType() != View.LAYER_TYPE_SOFTWARE) {
                    mView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
            } else {
                if (mView.getLayerType() != View.LAYER_TYPE_NONE) {
                    mView.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            }
        }

        // Offset any View rotation
        if (mShadowDrawable != null) {
            mShadowDrawable.setRotation(-mRotation);
        }
//        if (mBorderDrawable != null) {
//            mBorderDrawable.setRotation(-mRotation);
//        }
    }
}
