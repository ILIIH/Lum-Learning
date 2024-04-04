package com.example.theme_list_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.ask_answer_data.ResultOf
import com.example.core.ui.BaseFragment
import com.example.theme_list_ui.adapter.ThemeAdapter
import com.example.theme_list_ui.databinding.FragmentThemeListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThemeListFragment : BaseFragment() {

    private val viewModule: ThemeViewModule  by viewModel()
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

        viewModule._themes.onEach  { result ->
            when (result) {
                is ResultOf.Success -> {
                    dismissLoading()
                    if(result.value.isNotEmpty()){
                        view.createNewThemeTitle?.visibility = View.GONE
                        themeListAdapter.submitList(result.value)
                    }
                    else {
                        view.createNewThemeTitle?.visibility = View.VISIBLE
                    }
                }
                is ResultOf.Loading -> showLoading()
                is ResultOf.Failure -> {
                    dismissLoading()
                    showError(result.error)
                }
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        view.addNewTheme.setOnClickListener {
            viewModule.toAddNewTheme()
        }

        return view.root
    }
}
