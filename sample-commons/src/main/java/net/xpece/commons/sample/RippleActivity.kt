package net.xpece.commons.sample

import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import net.xpece.android.content.getDrawableCompat
import net.xpece.commons.android.sample.R

@RequiresApi(21)
class RippleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = AppCompatButton(this)
        button.background = button.context.getDrawableCompat(R.drawable.test_ripple)
        setContentView(button)
    }
}
