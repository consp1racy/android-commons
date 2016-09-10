package net.xpece.android.content

import net.xpece.android.content.res.Dimen

/**
 * @hide
 */
class DimenInitProvider : EmptyContentProvider() {
    override fun onCreate(): Boolean {
        Dimen.init(context)
        return false
    }
}
