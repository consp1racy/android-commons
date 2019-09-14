@file:JvmName("XpActivity")

package net.xpece.android.app

import android.app.Activity
import android.content.Intent
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder

@JvmOverloads
@Deprecated("Needs revision.")
fun Activity.navigateUpEx(intent: Intent = NavUtils.getParentActivityIntent(this)!!): Boolean {
    if (callingActivity != null) {
        finish() // Deliver result.
    } else {
        if (NavUtils.shouldUpRecreateTask(this, intent)) {
            val stackBuilder = TaskStackBuilder.create(this)
            stackBuilder.addNextIntentWithParentStack(intent)
            stackBuilder.startActivities()
            finish()
        } else {
            NavUtils.navigateUpTo(this, intent)
        }
    }
    return true
}
