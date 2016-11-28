package net.xpece.android.util

/**
 * Created by Eugen on 14.05.2016.
 */
interface ComparableContent<in T> {
    fun isSame(other: T): Boolean
    fun isContentSame(other: T): Boolean {
        return this == other
    }

    companion object {
        fun <T : ComparableContent<T>> sortByContent(left: Collection<T>, right: Collection<T>, outLeftOnly: MutableCollection<T>, outRightOnly: MutableCollection<T>, outChangedNew: MutableCollection<T>, outChangedOld: MutableCollection<T> = mutableListOf(), outSame: MutableCollection<T> = mutableListOf()) {
            val newLeft = mutableListOf<T>()
            val newRight = mutableListOf<T>()

            newLeft.addAll(left)
            newRight.addAll(right)

            for (item1 in left) {
                for (item2 in right) {
                    if (item1.isContentSame(item2)) {
                        outSame.add(item2)
                        newLeft.remove(item1)
                        newRight.remove(item2)
                    } else if (item1.isSame(item2)) {
                        outChangedOld.add(item1)
                        outChangedNew.add(item2)
                        newLeft.remove(item1)
                        newRight.remove(item2)
                    }
                }
            }

            outLeftOnly.addAll(newLeft)
            outRightOnly.addAll(newRight)
        }

        fun <T : ComparableContent<T>> Collection<T>.findSameItem(item: T): T? {
            for (other in this) {
                if (item.isSame(other)) return other
            }
            return null
        }
    }
}
