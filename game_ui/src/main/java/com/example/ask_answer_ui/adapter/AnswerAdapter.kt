package com.example.ask_answer_ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.add_new_card_data.model.Answer
import com.example.ask_answer_ui.databinding.AnswerItemBinding

class AnswerAdapter(private val goToNextCard: (answerResult:Boolean) -> Unit) : ListAdapter<Answer, AnswerAdapter.AnswerItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemViewHolder {
        val view = AnswerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return AnswerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class AnswerItemViewHolder(
        private val binding: AnswerItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answerItem: Answer) {
            with(binding) {
                cardView.setOnClickListener {
                    if (answerItem.correct) {
                        cardView.setCardBackgroundColor(Color.parseColor("#5EFF8D"))
                        goToNextCard(true)
                    } else {
                        cardView.setCardBackgroundColor(Color.parseColor("#FA5757"))
                        goToNextCard(false)
                    }
                }
                Title.text = answerItem.answer
                SubTitle.text = answerItem.description
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.answer == newItem.answer

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem
    }
}
