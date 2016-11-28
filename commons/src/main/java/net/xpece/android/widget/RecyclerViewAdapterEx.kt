package net.xpece.android.widget

import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import net.xpece.android.R

/**
 * Created by Eugen on 22.10.2016.
 */
abstract class RecyclerViewAdapterEx<T : RecyclerView.ViewHolder> : HeaderFooterRecyclerViewAdapter<T>() {
    companion object {
        internal const val EX_VIEW_TYPE_ERROR = 1
        internal const val EX_VIEW_TYPE_PROGRESS = 2
        internal const val EX_VIEW_TYPE_MESSAGE = 3
    }

    private var model: Model? = null

    fun showError(error: ErrorModel) {
        if (model == null) {
            model = error
            notifyPreFooterItemInserted(0)
        } else if (model != error) {
            model = error
            notifyPreFooterItemChanged(0)
        }
    }

    fun hideError() {
        if (model is ErrorModel) {
            model = null
            notifyPreFooterItemRemoved(0)
        }
    }

    @JvmOverloads
    fun showProgress(progress : ProgressModel = ProgressModel.INSTANCE) {
        if (model == null) {
            model = progress
            notifyPreFooterItemInserted(0)
        } else if (model != progress) {
            model = progress
            notifyPreFooterItemChanged(0)
        }
    }

    fun hideProgress() {
        if (model is ProgressModel) {
            model = null
            notifyPreFooterItemRemoved(0)
        }
    }

    fun showMessage(message: MessageModel) {
        if (model == null) {
            model = message
            notifyPreFooterItemInserted(0)
        } else if (model != message) {
            model = message
            notifyPreFooterItemChanged(0)
        }
    }

    fun hideMessage() {
        if (model is MessageModel) {
            model = null
            notifyPreFooterItemRemoved(0)
        }
    }

    fun hideAny() {
        if (model != null) {
            model = null
            notifyPreFooterItemRemoved(0)
        }
    }

    fun hideAnyExceptProgress() {
        if (model != null && model !is ProgressModel) {
            model = null
            notifyPreFooterItemRemoved(0)
        }
    }

    override val preFooterItemCount: Int
        get() = if (model == null) 0 else 1

    override fun getPreFooterItemViewType(position: Int): Int {
        return model!!.type
    }

    override fun onCreatePreFooterItemViewHolder(parent: ViewGroup, footerViewType: Int): RecyclerView.ViewHolder {
        val view = model!!.createView(parent)
        return EmptyViewHolder(view)
    }

    override fun onBindPreFooterItemViewHolder(footerViewHolder: RecyclerView.ViewHolder, position: Int) {
        model!!.bind(footerViewHolder)
    }

    internal class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Model {
        val type: Int
        @get:LayoutRes val layoutId : Int
        fun createView(parent: ViewGroup): View {
            val context = parent.context!!
            val inflater = LayoutInflater.from(context)!!
            return inflater.inflate(layoutId, parent, false)
        }
        fun bind(holder: RecyclerView.ViewHolder) {}
    }

    open class ProgressModel : Model {
        companion object {
            val INSTANCE = ProgressModel()
        }

        override final val type = EX_VIEW_TYPE_PROGRESS

        override val layoutId = R.layout.xpc_progress
    }

    open class MessageModel private constructor(
            internal val text: CharSequence?,
            @StringRes internal val textId: Int
    ) : Model {
        override final val type = EX_VIEW_TYPE_MESSAGE

        override val layoutId = R.layout.xpc_text

        override fun bind(holder: RecyclerView.ViewHolder) {
            val view = holder.itemView
            val textView = view.findViewById(android.R.id.text1) as TextView
            text?.apply { textView.text = this } ?: textView.setText(textId)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is MessageModel) return false

            if (text != other.text) return false
            if (textId != other.textId) return false

            return true
        }

        override fun hashCode(): Int {
            var result = text?.hashCode() ?: 0
            result = 31 * result + textId
            return result
        }

        constructor(text: CharSequence) : this(text, 0)

        constructor(@StringRes textId: Int) : this(null, textId)
    }

    open class ErrorModel private constructor(
            internal val text: CharSequence?,
            @StringRes internal val textId: Int,
            internal val buttonText: CharSequence?,
            @StringRes internal val buttonTextId: Int,
            internal val listener: View.OnClickListener
    ) : Model {
        override final val type = EX_VIEW_TYPE_ERROR

        override val layoutId = R.layout.xpc_try_again

        override fun bind(holder: RecyclerView.ViewHolder) {
            val view = holder.itemView
            val textView = view.findViewById(android.R.id.text1) as TextView
            val button = view.findViewById(android.R.id.button1) as Button
            text?.apply { textView.text = this } ?: textView.setText(textId)
            buttonText?.apply { button.text = this } ?: button.setText(buttonTextId)
            button.setOnClickListener(listener)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ErrorModel) return false

            if (text != other.text) return false
            if (textId != other.textId) return false
            if (buttonText != other.buttonText) return false
            if (buttonTextId != other.buttonTextId) return false
            if (listener != other.listener) return false

            return true
        }

        override fun hashCode(): Int {
            var result = text?.hashCode() ?: 0
            result = 31 * result + textId
            result = 31 * result + (buttonText?.hashCode() ?: 0)
            result = 31 * result + buttonTextId
            result = 31 * result + listener.hashCode()
            return result
        }

        constructor(@StringRes textId: Int, @StringRes buttonTextId: Int, listener: View.OnClickListener) : this(null, textId, null, buttonTextId, listener)

        constructor(@StringRes textId: Int, listener: View.OnClickListener) : this(null, textId, null, R.string.xpc_try_again, listener)

        constructor(text: CharSequence, buttonText: CharSequence, listener: View.OnClickListener) : this(text, 0, buttonText, 0, listener)

        constructor(text: CharSequence, listener: View.OnClickListener) : this(text, 0, null, R.string.xpc_try_again, listener)


    }
}
