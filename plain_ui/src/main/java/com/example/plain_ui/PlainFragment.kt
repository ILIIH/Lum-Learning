package com.example.plain_ui

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.plain_data.Task
import com.example.plain_ui.databinding.FragmentPlainBinding
import com.example.plain_ui.navigation.PlainNavigation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PlainFragment : Fragment() {

    private val viewModel: PlainViewModel by inject()
    private val navigation: PlainNavigation by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentPlainBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            viewModel.tasks.onEach { themes ->
                if (themes?.isEmpty() == true) {
                    renderEmptyThemes(view)
                } else {
                    themes?.let { render(view, it) }
                }
            }.collect()
        }

        return view.root
    }
    private fun renderEmptyThemes(view: FragmentPlainBinding){

        view.plainView.visibility = View.GONE

        view.ruleText.visibility = View.VISIBLE
        view.teatcher.visibility = View.VISIBLE
        view.messageBottomPart.visibility = View.VISIBLE

        SpannableString(getString(R.string.no_theme_created)).apply {
            val create = getString(R.string.create_theme)
            val createIndex = indexOf(create)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navigation.toCreateNewTheme()
                }
            }

            setSpan(clickableSpan, createIndex, createIndex + create.length, 0)
            setSpan(ForegroundColorSpan(resources.getColor(android.R.color.black)), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            setSpan(StyleSpan(Typeface.BOLD), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), createIndex, +createIndex+ create.length, 0)

            view.ruleText.text = this
            view.ruleText.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun render(view: FragmentPlainBinding, tasksList: List<Task>){
        view.plainView.visibility = View.VISIBLE

        view.ruleText.visibility = View.GONE
        view.teatcher.visibility = View.GONE
        view.messageBottomPart.visibility = View.GONE

        view.day.setOnClickListener {
            view.plainView.changePeriodType(getString(R.string.day))
        }
        view.week.setOnClickListener {
            view.plainView.changePeriodType(getString(R.string.week))
        }
        view.month.setOnClickListener {
            view.plainView.changePeriodType(getString(R.string.month))
        }

        view.plainView.setTasks(tasksList)
    }
}
