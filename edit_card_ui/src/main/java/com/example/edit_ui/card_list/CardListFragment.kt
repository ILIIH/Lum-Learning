package com.example.edit_ui.card_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.add_new_card_data.model.Card
import com.example.ask_answer_data.ResultOf
import com.example.core.ui.BaseFragment
import com.example.edit_ui.adapter.CardListAdapter
import com.example.edit_ui.databinding.FragmentCardListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class CardListFragment : BaseFragment() {

    val viewModel: CardListViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentCardListBinding.inflate(inflater, container, false)
        val listAdapter = CardListAdapter { id  -> viewModel.deleteCardById(id) }

        viewModel.downloadCards(CardListFragmentArgs.fromBundle(requireArguments()).themeId)

        view.list.adapter = listAdapter
        viewModel._cardList.onEach{
            when (it) {
                is ResultOf.Success -> {
                    dismissLoading()
                    listAdapter.setListData(ArrayList(it.value))
                }
                is ResultOf.Failure -> {
                    dismissLoading()
                    showError(it.error)
                }
                is ResultOf.Loading -> {
                    showLoading()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return view.root
    }
}
