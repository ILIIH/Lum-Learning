package com.example.add_new_card.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.add_new_card.R
import com.example.add_new_card.databinding.AnswerAdapterItemBinding
import com.example.add_new_card_data.model.Answer

class AnswersAdapters : ListAdapter<Answer, AnswersAdapters.AnswerItemViewHolder>(DiffCallBack()) {

    val bindings = mutableListOf<AnswerAdapterItemBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemViewHolder {
        val view = AnswerAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return AnswerItemViewHolder(view)
    }

    fun validateAnswers(): Boolean {
        var isAnswerInvalid = false
        bindings.forEach {
            if(it.answer.text.isEmpty()){
                isAnswerInvalid = true
                it.answerLayout.setBackgroundResource(R.drawable.answer_false_text_background)
                it.answerLayout.requestFocus()
            }
            else {
                it.answerLayout.setBackgroundResource(R.drawable.answer_true_text_background)
            }
            if(it.hint.isChecked &&  it.answerHint.text.isEmpty()){
                isAnswerInvalid = true
                it.answerHintLayout.setBackgroundResource(R.drawable.answer_false_text_background)
                it.answerHintLayout.requestFocus()
            }
            else {
                it.answerHintLayout.setBackgroundResource(R.drawable.answer_true_text_background)
            }
        }
        bindings.clear()
        return isAnswerInvalid
    }
    fun getAllAnswers() = bindings.map {
            Answer(
            answer = it.answer.text.toString(),
            description = it.answerHint.text.toString(),
            correct = it.answerTrue.isChecked,
        )
    }
    override fun onBindViewHolder(holder: AnswerItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class AnswerItemViewHolder(
        private val binding: AnswerAdapterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            clearAnswerItem()

            binding.answer.addTextChangedListener {
                binding.answerLayout.setBackgroundResource(R.drawable.answer_true_text_background)
            }
            binding.answerHint.addTextChangedListener {
                binding.answerHintLayout.setBackgroundResource(R.drawable.answer_true_text_background)
            }

            binding.answerTrue.setOnClickListener { }
            binding.hint.setOnClickListener {
                binding.answerHintLayout.visibility = if (binding.answerHintLayout.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
            bindings.add(binding)
        }

        private fun clearAnswerItem() {
            binding.answer.text.clear()
            binding.hint.isChecked = false
            binding.answerHint.text.clear()
            binding.answerHintLayout.visibility = View.GONE
            binding.answerTrue.isChecked = false
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.answer == newItem.answer

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem
    }
}
