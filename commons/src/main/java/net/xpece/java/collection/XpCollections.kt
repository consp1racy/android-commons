@file:JvmName("XpCollections")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.java.collection

/**
 * Returns [this] if it is an [ArrayList], otherwise copies elements into new [ArrayList].
 */
inline fun <E> Collection<E>.asArrayList(): ArrayList<E> = this as? ArrayList<E> ?: ArrayList(this)
