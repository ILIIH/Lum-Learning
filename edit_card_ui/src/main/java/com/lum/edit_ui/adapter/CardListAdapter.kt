package com.lum.edit_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lum.add_new_card_data.model.Card
import com.lum.edit_ui.databinding.CardItemBinding

class CardListAdapter(private val deleteCard: (id: Int) -> Unit) : ListAdapter<Card, CardListAdapter.CardItemViewHolder>(
    DiffCallBack()
) {

    private val list = ArrayList<Card>(20)

    fun setListData(data: ArrayList<Card>) {
        list.addAll(data)
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        val view = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CardItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    inner class CardItemViewHolder(
        private val binding: CardItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardItem: Card) {
            with(binding) {
                question.text = cardItem.question
                deleteBtn.setOnClickListener {
                    deleteCard(cardItem.id)
                    list.remove(cardItem)
                    notifyDataSetChanged()
                }
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            (oldItem.id == newItem.id)&& (oldItem.answers.containsAll(newItem.answers))
    }
}
