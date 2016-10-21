@file:JvmName("XpActivity")

package net.xpece.android.app

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.support.v4.app.NavUtils

/**
 * Created by Eugen on 18.10.2016.
 */
@TargetApi(16)
fun Activity.navigateUpEx(intent: Intent = NavUtils.getParentActivityIntent(this)): Boolean {
    if (NavUtils.shouldUpRecreateTask(this, intent)) {
        NavUtils.navigateUpTo(this, intent)
        return true
    } else {
        if (callingActivity != null) {
            finish() // Deliver result.
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
        return true
    }
}
