package com.example.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.core.R
import com.example.core.domain.ILError
import com.example.core.ui.dialog.ErrorDialogFragment
import com.example.core.ui.dialog.LoadingFragment

open class BaseFragment : Fragment() {

    var loadingFragment: LoadingFragment? = null
    fun showError(error: ILError?) {
        val messageArgs = Bundle()
        when (error) {
            ILError.VALIDATION_TITLE -> {
                messageArgs.putString(ErrorDialogFragment.error_message_tag, getString(R.string.empty_title_error))
            }
            ILError.VALIDATION_PHOTO -> {
                messageArgs.putString(ErrorDialogFragment.error_message_tag, getString(R.string.theme_photo_error))
            }
            ILError.VALIDATION_YEAR -> {
                messageArgs.putString(ErrorDialogFragment.error_message_tag, getString(R.string.years_error))
            }
            ILError.IO_ERROR -> {
                messageArgs.putString(ErrorDialogFragment.error_message_tag, getString(R.string.io_error))
            }

            else -> {
                messageArgs.putString(ErrorDialogFragment.error_message_tag, getString(R.string.empty_title_error))
            }
        }
        val errorDialog = ErrorDialogFragment()
        errorDialog.arguments = messageArgs
        errorDialog.show(childFragmentManager, ErrorDialogFragment.error_message_tag)
    }

        fun showLoading(){
            if (loadingFragment == null) {
                loadingFragment = LoadingFragment()
                loadingFragment?.show(parentFragmentManager, LoadingFragment.loading_fragment_tag)
            }
        }

    fun dismissLoading(){
        loadingFragment?.let {
            if (it.isAdded && !it.isHidden) {
                it.dismiss()
            }
            loadingFragment = null
        }
    }

}
