package com.example.edit_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edit_ui.data.Card
import com.example.edit_ui.data.CardType
import com.example.edit_ui.databinding.CardItemBinding

class CardListAdapter(private val deleteCard: (id: Int, type: CardType) -> Unit) : ListAdapter<Card, CardListAdapter.CardItemViewHolder>(DiffCallBack()) {

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
                    cardItem.type
                    deleteCard(cardItem.id, cardItem.type)
                    list.remove(cardItem)
                    notifyDataSetChanged()
                }
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            (oldItem.id == newItem.id) && (oldItem.type == newItem.type)

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem == newItem
    }
}
