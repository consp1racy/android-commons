package net.xpece.android.text

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import net.xpece.android.app.inputMethodManager

object Keyboards {

    fun showKeyboard(view: View) {
        view.context.inputMethodManager
                .showSoftInput(view, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val currentFocus = activity.currentFocus
        if (currentFocus != null) {
            currentFocus.clearFocus()
            activity.inputMethodManager
                    .hideSoftInputFromWindow(currentFocus.windowToken, HIDE_NOT_ALWAYS)
        }
    }
}
