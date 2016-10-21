package net.xpece.android.text

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Keyboards {

    fun showKeyboard(view: View) {
        getInputManager(view.context).showSoftInput(view, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val currentFocus = activity.currentFocus
        currentFocus.clearFocus()
        if (currentFocus != null) {
            getInputManager(activity).hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun getInputManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}
