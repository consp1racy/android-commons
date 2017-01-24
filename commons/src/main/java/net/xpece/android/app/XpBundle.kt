package net.xpece.android.app

import android.os.Bundle
import android.os.ParcelUuid
import java.util.*

/// Kotlin > 1.1 - using bound callable references =================================================

//@JvmName("putBoolean")
//fun Bundle.put(property: KProperty0<Boolean>) {
//    putBoolean(property.name, property.get())
//}
//
//@JvmName("putInt")
//fun Bundle.put(property: KProperty0<Int>) {
//    putInt(property.name, property.get())
//}
//
//@JvmName("putLong")
//fun Bundle.put(property: KProperty0<Long>) {
//    putLong(property.name, property.get())
//}

/// Kotlin < 1.1 ===================================================================================

//@JvmName("getBoolean")
//fun <T> Bundle.get(property: KMutableProperty1<T, Boolean>, owner: T, defaultValue: Boolean = false) {
//    val value = getBoolean(property.name, defaultValue)
//    property.set(owner, value)
//}
//
//@JvmName("getInt")
//fun <T> Bundle.get(property: KMutableProperty1<T, Int>, owner: T, defaultValue: Int = 0) {
//    val value = getInt(property.name, defaultValue)
//    property.set(owner, value)
//}
//
//@JvmName("getLong")
//fun <T> Bundle.get(property: KMutableProperty1<T, Long>, owner: T, defaultValue: Long = 0L) {
//    val value = getLong(property.name, defaultValue)
//    property.set(owner, value)
//}
//
//@JvmName("putBoolean")
//fun <T> Bundle.put(property: KProperty1<T, Boolean>, owner: T) {
//    putBoolean(property.name, property.get(owner))
//}
//
//@JvmName("putInt")
//fun <T> Bundle.put(property: KProperty1<T, Int>, owner: T) {
//    putInt(property.name, property.get(owner))
//}
//
//@JvmName("putLong")
//fun <T> Bundle.put(property: KProperty1<T, Long>, owner: T) {
//    putLong(property.name, property.get(owner))
//}

/// Getters ========================================================================================

//@JvmName("getBoolean")
//fun Bundle.get(property: KProperty<Boolean>, defaultValue: Boolean = false) {
//    getBoolean(property.name, defaultValue)
//}
//
//@JvmName("getInt")
//fun Bundle.get(property: KProperty<Int>, defaultValue: Int = 0) {
//    getInt(property.name, defaultValue)
//}
//
//@JvmName("getLong")
//fun Bundle.get(property: KProperty<Int>, defaultValue: Long = 0L) {
//    getLong(property.name, defaultValue)
//}
