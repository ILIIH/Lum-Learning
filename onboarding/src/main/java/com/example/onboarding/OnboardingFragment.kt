package com.example.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.add_theme_ui.OnboardingViewModel
import com.example.onboarding.databinding.FragmentOnboardingBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment : Fragment() {

    val viewModule: OnboardingViewModel  by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentOnboardingBinding.inflate(inflater, container, false)
        view.startButton.setOnClickListener {
            viewModule.navigateToThemeList()
        }
        view.plainButton.setOnClickListener {
            viewModule.navigateToPlain()
        }

        return view.root
    }
}
