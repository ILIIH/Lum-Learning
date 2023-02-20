package com.example.ask_answer_ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ask_answer_data.Answer
import com.example.ask_answer_ui.databinding.AnswerItemBinding

class AnswerAdapter : ListAdapter<Answer, AnswerAdapter.AnswerItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemViewHolder {
        val view = AnswerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AnswerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, currentList.size - 1 == position)
    }

    inner class AnswerItemViewHolder(
        private val binding: AnswerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answerItem: Answer, isLast: Boolean) {
            with(binding) {
                Title.text = answerItem.answer
                SubTitle.text = answerItem.association
                if (isLast)setMargins(cardView, 0, 150, 0, 0)
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.answer == newItem.answer

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }
}
