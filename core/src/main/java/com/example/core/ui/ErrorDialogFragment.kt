package com.example.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.core.databinding.ErrorDialogBinding

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = ErrorDialogBinding.inflate(inflater, container, false)
        val errorMessage = arguments?.getString(error_message_tag)
        view.errorText.text = errorMessage
        view.exitIcon.setOnClickListener {
            dismiss()
        }
        return view.root
    }

    companion object {
        const val error_message_tag = "error_message"
    }
}
