package com.pluscubed.recyclerfastscroll

/**
 * Call this method in [android.support.v4.app.Fragment.onDestroyView] along with
 * * [com.pluscubed.recyclerfastscroll.RecyclerFastScroller.attachAdapter(null)] and
 * * [android.support.v7.widget.RecyclerView.clearOnScrollListeners()]
 */
fun RecyclerFastScroller.clearRecyclerViewReference() {
    mRecyclerView = null
}
