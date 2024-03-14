package com.example.add_theme_ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.add_theme_ui.databinding.ThemeNameDialogBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ThemeNameFragment : DialogFragment() {

    private val viewModule: ThemeAddViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ThemeNameDialogBinding.inflate(inflater, container, false)

        dialog?.setCancelable(false);

        view.continueButton.setOnClickListener {
            if (viewModule.setThemeName(view.themeTitleTextInput.text.toString())) {
                dismiss()
            }
            else {
                view.errorText.visibility = View.VISIBLE
            }

        }
        return view.root
    }

    companion object {
        const val THEME_NAME_FRAGMENT_TAG = "ThemeNameDialogFragmentTag"
    }
}