@file:JvmName("XpActionBar")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.support.v7.app.ActionBar

inline fun ActionBar.setDisplayShowTitleAndHomeAsUp() {
    setDisplayShowTitleEnabled(true)
    setDisplayHomeAsUpEnabled(true)
    setDisplayShowHomeEnabled(true)
//    displayOptions = ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_HOME_AS_UP or ActionBar.DISPLAY_SHOW_HOME
}
