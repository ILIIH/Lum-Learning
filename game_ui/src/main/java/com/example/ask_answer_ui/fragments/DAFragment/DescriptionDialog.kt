package com.example.ask_answer_ui.fragments.DAFragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ask_answer_ui.databinding.DescriptionDialogBinding

class DescriptionDialog(
    private val description: String
) : DialogFragment() {

    private lateinit var binding: DescriptionDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DescriptionDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.continueBtn.setOnClickListener { dismiss() }
        binding.description.text = description

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    companion object{
        const val DESCRIPTION_DIALOG_TAG = "description_dialog_tag"
    }
}
