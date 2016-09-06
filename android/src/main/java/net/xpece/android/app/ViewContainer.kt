package net.xpece.android.app

import android.app.Activity
import android.view.ViewGroup

/** An indirection which allows controlling the root container used for each activity.  */
interface ViewContainer {
    /** The root [ViewGroup] into which the activity should place its contents.  */
    fun <T> forActivity(activity: T): ViewGroup where T : Activity, T : CustomRootActivity

    companion object {
        /** An [ViewContainer] which returns the normal activity content view.  */
        val DEFAULT = object : ViewContainer {
            override fun <T> forActivity(activity: T): ViewGroup where T : Activity, T : CustomRootActivity {
                return activity.findViewById(android.R.id.content) as ViewGroup
            }
        }
    }
}
