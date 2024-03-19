package com.example.theme_list_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.theme_list_ui.adapter.ThemeAdapter
import com.example.theme_list_ui.databinding.FragmentThemeListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ThemeListFragment : Fragment() {

    private val viewModule: ThemeViewModule by inject()
    private val navigator: ThemeListNavigation by inject()
    lateinit var themeListAdapter: ThemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeListBinding.inflate(inflater, container, false)

        viewModule.loadThemeList()

        themeListAdapter = ThemeAdapter(navigator)
        view.themeList.adapter = themeListAdapter

        viewModule._themes.onEach  {
            themeListAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        view.addNewTheme.setOnClickListener {
            viewModule.toAddNewTheme()
        }

        return view.root
    }
}
