package com.example.ask_answer_ui.fragments.DAFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.ask_answer_ui.databinding.DescriptionDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CardEndsDialog(
    private val description: String
) : DialogFragment() {

    private lateinit var binding: DescriptionDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DescriptionDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.description.text = description

        lifecycleScope.launch {
            delay(8000)
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    companion object{
        const val DESCRIPTION_DIALOG_TAG = "description_dialog_tag"
    }
}
