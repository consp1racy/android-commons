//@file:Suppress("unused")
//
//package net.xpece.android.app
//
//import android.content.Context
//import android.support.annotation.RequiresApi
//import java.lang.ref.WeakReference
//import java.util.*
//import kotlin.reflect.KProperty
//
//private val systemServiceDelegates = object :
//        ThreadLocal<WeakHashMap<Context, SystemServiceDelegate>>() {
//    override fun initialValue() = WeakHashMap<Context, SystemServiceDelegate>()
//}
//
//val Context.systemServiceDelegate: SystemServiceDelegate
//    get() {
//        val delegates = systemServiceDelegates.get()
//        var delegate = delegates[this]
//        if (delegate == null) {
//            delegate = SystemServiceDelegate(this)
//            delegates[this] = delegate
//        }
//        return delegate
//    }
//
//class SystemServiceDelegate internal constructor(context: Context) {
//    private val contextRef = WeakReference(context)
//    val context = contextRef.get()!!
//
//    @RequiresApi(23)
//    inline operator fun <reified S> getValue(thisRef: Any?, property: KProperty<*>): S {
//        return context.getSystemServiceOrThrow()
//    }
//}
