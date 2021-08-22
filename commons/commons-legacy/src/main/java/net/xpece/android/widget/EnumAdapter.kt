package net.xpece.android.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

open class EnumAdapter<T : Enum<T>> @JvmOverloads constructor(
        enumType: Class<T>,
        private val showNull: Boolean = false
) : BindableAdapter<T>() {

    private val enumConstants: Array<T> = enumType.enumConstants!!
    private val nullOffset: Int = if (showNull) 1 else 0

    override fun getCount(): Int = enumConstants.size + nullOffset

    override fun getItem(position: Int): T? {
        return if (showNull && position == 0) null
        else enumConstants[position - nullOffset]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun newView(inflater: LayoutInflater, position: Int, container: ViewGroup): View {
        return inflater.inflate(android.R.layout.simple_spinner_item, container, false)
    }

    override fun bindView(item: T?, position: Int, view: View) {
        val tv = view.findViewById<View>(android.R.id.text1) as TextView
        tv.text = getName(item)
    }

    override fun newDropDownView(inflater: LayoutInflater, position: Int, container: ViewGroup): View {
        return inflater.inflate(android.R.layout.simple_spinner_dropdown_item, container, false)
    }

    protected open fun getName(item: T?): String = item.toString()
}
