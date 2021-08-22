package net.xpece.android.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.animation.Animation

/**
 * A version of fragment which does not replay its enter animation after configuration change.
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {
    companion object {
        internal const val s = "mPlayedEnterAnimation"
    }

    private var mPlayedEnterAnimation: Boolean = false

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            if (mPlayedEnterAnimation) {
                return object : Animation() {}
            } else {
                mPlayedEnterAnimation = true
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            mPlayedEnterAnimation = savedInstanceState.getBoolean(s)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(s, mPlayedEnterAnimation)
    }
}
