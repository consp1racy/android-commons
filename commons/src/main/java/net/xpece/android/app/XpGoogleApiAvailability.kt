package net.xpece.android.app

import android.app.Dialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.SupportErrorDialogFragment

/**
 * Created by Eugen on 23.12.2016.
 */
object XpGoogleApiAvailability {
    @JvmOverloads
    fun showErrorDialogFragment(fragment: androidx.fragment.app.Fragment, errorCode: Int, requestCode: Int, cancelListener: DialogInterface.OnCancelListener? = null): Boolean {
        val activity = fragment.activity
        val dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, errorCode, requestCode, cancelListener)
        if (dialog == null) {
            return false
        } else {
            val fm = fragment.fragmentManager!!
            showErrorDialogFragment(fm, dialog, "GooglePlayServicesErrorDialog", cancelListener)
            return true
        }
    }

    private fun showErrorDialogFragment(fm: androidx.fragment.app.FragmentManager, dialog: Dialog, tag: String, cancelListener: DialogInterface.OnCancelListener?) {
        val f = SupportErrorDialogFragment.newInstance(dialog, cancelListener)
        f.show(fm, tag)
    }
}
