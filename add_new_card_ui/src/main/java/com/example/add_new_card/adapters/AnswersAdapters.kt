package com.example.add_new_card.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.add_new_card.databinding.AnswerAdapterItemBinding
import com.example.add_new_card_data.model.Answer

class AnswersAdapters : ListAdapter<Answer, AnswersAdapters.AnswerItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemViewHolder {
        val view = AnswerAdapterItemBinding.inflate(
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
        private val binding: AnswerAdapterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answerItem: Answer) {
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.answer == newItem.answer

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem
    }
}
