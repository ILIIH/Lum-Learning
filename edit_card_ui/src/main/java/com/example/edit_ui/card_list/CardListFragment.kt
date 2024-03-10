package com.example.edit_ui.card_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ask_answer_data.ResultOf
import com.example.core.ui.BaseFragment
import com.example.edit_ui.adapter.CardListAdapter
import com.example.edit_ui.data.CardType
import com.example.edit_ui.databinding.FragmentCardListBinding
import org.koin.android.ext.android.inject

class CardListFragment : BaseFragment() {

    val viewModel: CardListViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentCardListBinding.inflate(inflater, container, false)
        val listAdapter = CardListAdapter { id, type ->
            when (type) {
                CardType.LEARNING_CARD -> viewModel.deleteLearningCardById(id)
                CardType.VA_CARD -> viewModel.deleteVACardById(id)
                CardType.SL_CARD -> viewModel.deleteALCardById(id)
            }
        }

        // TODO: Change to safe arg
        val themeId = arguments?.getInt("id", 0) ?: 0
        viewModel.downloadCards(themeId)

        view.list.adapter = listAdapter
        viewModel._cardList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultOf.Success -> {
                    dismissLoading()
                    listAdapter.setListData(it.value)
                }
                is ResultOf.Failure -> {
                    dismissLoading()
                    showError(it.error)
                }
                is ResultOf.Loading -> {
                    showLoading()
                }
            }
        }
        return view.root
    }
}
