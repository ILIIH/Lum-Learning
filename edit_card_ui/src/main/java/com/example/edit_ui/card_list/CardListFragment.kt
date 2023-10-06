package com.example.edit_ui.card_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ask_answer_data.ResultOf
import com.example.edit_ui.adapter.CardListAdapter
import com.example.edit_ui.data.CardType
import com.example.edit_ui.databinding.FragmentCardListBinding
import org.koin.android.ext.android.inject

class CardListFragment : Fragment() {

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

        val themeId = arguments?.getInt("id", 0) ?: 0
        viewModel.downloadCards(themeId)

        view.list.adapter = listAdapter
        viewModel._cardList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultOf.Success -> {
                    listAdapter.setListData(it.value)
                }
            }
        }
        return view.root
    }
}
