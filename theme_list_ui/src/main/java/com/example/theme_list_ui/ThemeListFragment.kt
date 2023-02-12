package com.example.theme_list_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theme_list_ui.adapter.ProfileAdapter
import com.example.theme_list_ui.databinding.FragmentThemeListBinding
import org.koin.android.ext.android.inject

class ThemeListFragment : Fragment() {

    val viewModule: ThemeViewModule by inject()
    val themeListAdapter: ProfileAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeListBinding.inflate(inflater, container, false)

        view.themeList.adapter = themeListAdapter

        viewModule._themes.observe(requireActivity()) {
            themeListAdapter.submitList(it)
        }

        view.addNewTheme.setOnClickListener {
            viewModule.toAddNewTheme()
        }

        return view.root
    }
}
