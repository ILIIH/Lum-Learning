package com.example.ask_answer_ui.fragments.RuleFragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.databinding.DescriptionDialogBinding
import java.util.*

class CardEndDialog(
    private val correctAsw: () -> Unit,
    private val wrongAsw: () -> Unit,
) : DialogFragment() {

    private lateinit var binding: DescriptionDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = android.app.AlertDialog.Builder(context).apply {
            setTitle("Cards ended")
            setMessage("You had repeated all cards, congratulation! You answered for a lot of card truly")
            setPositiveButton(
                getString(R.string.repeat_again),
            ) { dialog, _ ->
                dialog.dismiss()
                dialog.cancel()
                correctAsw()
            }
            setNegativeButton(
                R.string.exit,
            ) { dialog, _ ->
                dialog.dismiss()
                dialog.cancel()
                wrongAsw()
            }
        }
        return dialogBuilder.create()
    }
}
