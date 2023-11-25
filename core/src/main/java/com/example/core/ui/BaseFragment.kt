package com.example.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.core.R
import com.example.core.domain.ILError

const val ERROR_FRAGMENT_TAG = "ERROR_FRAGMENT"

open class BaseFragment : Fragment() {
    fun showError(error: ILError) {
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
        }
        val errorDialog = ErrorDialogFragment()
        errorDialog.arguments = messageArgs
        errorDialog.show(childFragmentManager, ERROR_FRAGMENT_TAG)
    }
}
