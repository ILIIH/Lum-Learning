package com.example.edit_ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.ask_answer_data.ResultOf
import com.example.core.ui.BaseFragment
import com.example.edit_ui.R
import com.example.edit_ui.adapter.CardListAdapter
import com.example.edit_ui.databinding.FragmentCardListBinding
import com.example.edit_ui.navigator.EditCardNavigation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardListFragment : BaseFragment() {

    val viewModel: CardListViewModel  by viewModel()
    val navigator: EditCardNavigation by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentCardListBinding.inflate(inflater, container, false)
        val listAdapter = CardListAdapter { id  -> viewModel.deleteCardById(id) }
        val themeId = CardListFragmentArgs.fromBundle(requireArguments()).themeId
        viewModel.downloadCards(themeId)

        view.list.adapter = listAdapter
        viewModel._cardList.onEach{
            when (it) {
                is ResultOf.Success -> {
                    dismissLoading()
                    if(it.value.isNotEmpty()){
                        listAdapter.setListData(ArrayList(it.value))
                    }
                    else {
                        renderEmptyCardList(view, themeId)
                    }
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

    fun renderEmptyCardList(view: FragmentCardListBinding, themeInt: Int){
        view.list.visibility = View.GONE
        view.teacher.root.visibility = View.VISIBLE

        view.teacher.ruleText.visibility = View.VISIBLE
        view.teacher.messageBottomPart.visibility = View.VISIBLE

        SpannableString("No card was created. Create card and return back to see card list").apply {
            val create = "Create card"
            val createIndex = indexOf(create)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navigator.fromEditCardToAddNewCard(themeInt)
                }
            }

            setSpan(clickableSpan, createIndex, createIndex + create.length, 0)
            setSpan(ForegroundColorSpan( ContextCompat.getColor(requireContext(),android.R.color.black )),
                createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            setSpan(StyleSpan(Typeface.BOLD), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), createIndex, +createIndex+ create.length, 0)

            view.teacher.ruleText.text = this
            view.teacher.ruleText.movementMethod = LinkMovementMethod.getInstance()
        }

    }
}
