package com.birbit.android.jobqueue.scheduling

import android.content.Context
import androidx.annotation.RequiresApi

/**
 * @author Eugen on 23.08.2016.
 */
object SchedulerFactory {
    @JvmStatic
    fun createSchedulerForGcmJobSchedulerService(
            appContext: Context, klass: Class<out GcmJobSchedulerService>): Scheduler =
            GcmJobSchedulerService.createSchedulerFor(appContext, klass)

    @JvmStatic
    @RequiresApi(21)
    fun createSchedulerForFrameworkJobSchedulerService(
            appContext: Context, klass: Class<out FrameworkJobSchedulerService>): Scheduler =
            FrameworkJobSchedulerService.createSchedulerFor(appContext, klass)
}
