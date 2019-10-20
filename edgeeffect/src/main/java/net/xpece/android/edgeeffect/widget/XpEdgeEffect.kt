@file:JvmName("XpEdgeEffect")

package net.xpece.android.edgeeffect.widget

import android.widget.AbsListView
import android.widget.EdgeEffect
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.getRecyclerView
import net.xpece.android.edgeeffect.horizontalscrollview.HorizontalScrollViewEdgeEffect
import net.xpece.android.edgeeffect.listview.AbsListViewEdgeEffect
import net.xpece.android.edgeeffect.nestedscrollview.NestedScrollViewEdgeEffect
import net.xpece.android.edgeeffect.scrollview.ScrollViewEdgeEffect
import net.xpece.android.edgeeffect.viewpager.ViewPagerEdgeEffect

@ColorInt
fun EdgeEffect.getColorCompat(): Int {
    return ColorEdgeEffect.IMPL.getColor(this)
}

fun EdgeEffect.setColorCompat(@ColorInt color: Int) {
    ColorEdgeEffect.IMPL.setColor(this, color)
}

fun AbsListView.setEdgeEffectColorCompat(@ColorInt color: Int) {
    AbsListViewEdgeEffect.IMPL.setEdgeEffectColor(this, color)
}

fun AbsListView.setTopEdgeEffectColorCompat(@ColorInt color: Int) {
    AbsListViewEdgeEffect.IMPL.setTopEdgeEffectColor(this, color)
}

fun AbsListView.setBottomEdgeEffectColorCompat(@ColorInt color: Int) {
    AbsListViewEdgeEffect.IMPL.setBottomEdgeEffectColor(this, color)
}

fun ScrollView.setEdgeEffectColorCompat(@ColorInt color: Int) {
    ScrollViewEdgeEffect.IMPL.setEdgeEffectColor(this, color)
}

fun ScrollView.setTopEdgeEffectColorCompat(@ColorInt color: Int) {
    ScrollViewEdgeEffect.IMPL.setTopEdgeEffectColor(this, color)
}

fun ScrollView.setBottomEdgeEffectColorCompat(@ColorInt color: Int) {
    ScrollViewEdgeEffect.IMPL.setBottomEdgeEffectColor(this, color)
}

fun HorizontalScrollView.setEdgeEffectColorCompat(@ColorInt color: Int) {
    HorizontalScrollViewEdgeEffect.IMPL.setEdgeEffectColor(this, color)
}

fun HorizontalScrollView.setLeftEdgeEffectColorCompat(@ColorInt color: Int) {
    HorizontalScrollViewEdgeEffect.IMPL.setLeftEdgeEffectColor(this, color)
}

fun HorizontalScrollView.setRightEdgeEffectColorCompat(@ColorInt color: Int) {
    HorizontalScrollViewEdgeEffect.IMPL.setRightEdgeEffectColor(this, color)
}

fun NestedScrollView.setEdgeEffectColorCompat(@ColorInt color: Int) {
    NestedScrollViewEdgeEffect.setEdgeEffectColor(this, color)
}

fun NestedScrollView.setTopEdgeEffectColorCompat(@ColorInt color: Int) {
    NestedScrollViewEdgeEffect.setTopEdgeEffectColor(this, color)
}

fun NestedScrollView.setBottomEdgeEffectColorCompat(@ColorInt color: Int) {
    NestedScrollViewEdgeEffect.setBottomEdgeEffectColor(this, color)
}

fun ViewPager.setEdgeEffectColorCompat(@ColorInt color: Int) {
    ViewPagerEdgeEffect.setEdgeEffectColor(this, color)
}

fun ViewPager.setLeftEdgeEffectColorCompat(@ColorInt color: Int) {
    ViewPagerEdgeEffect.setLeftEdgeEffectColor(this, color)
}

fun ViewPager.setRightEdgeEffectColorCompat(@ColorInt color: Int) {
    ViewPagerEdgeEffect.setRightEdgeEffectColor(this, color)
}

fun ViewPager2.setEdgeEffectFactoryCompat(factory: RecyclerView.EdgeEffectFactory) {
    getRecyclerView().edgeEffectFactory = factory
}
