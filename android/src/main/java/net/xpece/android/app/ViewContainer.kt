package net.xpece.android.app

import android.R
import android.view.ViewGroup

/** An indirection which allows controlling the root container used for each activity.  */
interface ViewContainer {
    /** The root [ViewGroup] into which the activity should place its contents.  */
    fun <T : BaseActivity> forActivity(activity: T): ViewGroup

    companion object {
        /** An [ViewContainer] which returns the normal activity content view.  */
        val DEFAULT = object : ViewContainer {
            override fun <T : BaseActivity> forActivity(activity: T): ViewGroup {
                return activity.findViewById(R.id.content) as ViewGroup
            }
        }
    }
}
