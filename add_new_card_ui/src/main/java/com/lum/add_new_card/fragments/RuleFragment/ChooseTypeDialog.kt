package com.lum.add_new_card.fragments.RuleFragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.lum.add_new_card.databinding.ChooseTypeDialogBinding
import com.lum.core.domain.Scopes
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class ChooseTypeDialog() : DialogFragment(), AndroidScopeComponent {

    private lateinit var themeInfoProvider: ThemeInfoProvider
    val ourSession = getKoin().getOrCreateScope(Scopes.ADD_NEW_CARD_SCOPE.scope, named(Scopes.ADD_NEW_CARD_SCOPE.scope))
    override val scope: Scope by fragmentScope()

    private lateinit var binding: ChooseTypeDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ChooseTypeDialogBinding.inflate(LayoutInflater.from(context))

        binding.answerRandom.setOnClickListener {
            lifecycleScope.launch {
                themeInfoProvider.generatePrediction(true)
                dismiss()
            }
        }

        binding.answerAi.setOnClickListener {
            lifecycleScope.launch {
                themeInfoProvider.generatePrediction(true)
                dismiss()
            }
        }

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onAttach(context: Context) {
        scope.linkTo(ourSession)
        themeInfoProvider = get<ThemeInfoProvider>()
        super.onAttach(context)
    }

    companion object{
        const val CHOOSE_TYPE_DIALOG_TAG = "choose_type_dialog_tag"
    }



}