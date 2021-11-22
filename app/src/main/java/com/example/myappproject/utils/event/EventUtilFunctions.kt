package com.app.projectname.utils.event
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.app.neomads.ui.components.customDialog.CustomDialog
import com.app.neomads.utils.extensions.safeNavigateFromNavController
import com.example.myappproject.R
import com.example.myappproject.utils.Constants
import com.example.myappproject.utils.ProgressDialog
import com.google.android.material.snackbar.Snackbar

object EventUtilFunctions {
    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showAlert(
        message: String,
        context: Context,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String? = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null,
    ) {
        val customDialog = CustomDialog(
            context,
            R.style.CustomDialogStyle,
            message = message,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive
        )
        customDialog.setCancelable(false)
        customDialog.show()
    }

    fun showLoader(show: Boolean, customProgressDialog: ProgressDialog?) {
        if (show) {
            customProgressDialog?.apply {
                if (!isShowing) show()
            }
        } else {
            customProgressDialog?.apply {
                dismiss()
            }
        }
    }

    fun showSnackBar(view: View, message: String, action: (() -> Unit)? = null) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackBar.setAction("Retry") {
                it()
            }
        }
        snackBar.show()
    }
    fun navigateByDirections(fragment: Fragment, navDirections: NavDirections) {
    fragment.safeNavigateFromNavController(navDirections)
    }

}

