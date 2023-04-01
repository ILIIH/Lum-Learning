package com.example.add_new_card.fragments.RuleFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.databinding.FragmentRuleBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class RuleFragment : Fragment() {

    val viewModel: MainFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentRuleBinding.inflate(inflater, container, false)
        view.startButton.setOnClickListener {
            findNavController().navigate(R.id.to_addVA_Fragment)
        }

        viewModel.setThemeId(requireArguments().getInt("id"))
        return view.root
    }
}
