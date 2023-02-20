package com.example.formula_builder_ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.formula_builder_ui.Category
import com.example.formula_builder_ui.databinding.BigOperatorItemBinding
import com.example.formula_builder_ui.operationIcon

class CategoryIconAdapter(
    private val expandGroup: (type: Category?) -> Unit
) :
    ListAdapter<operationIcon, CategoryIconAdapter.CategoryIconViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryIconViewHolder {
        val view = BigOperatorItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryIconViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryIconViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CategoryIconViewHolder(
        private val binding: BigOperatorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(operationItem: operationIcon) {
            with(binding) {
                sumbol.setImageBitmap(operationItem.image)
                operatorBackground.setOnClickListener {
                    expandGroup(operationItem.categoryType)
                }
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<operationIcon>() {
        override fun areItemsTheSame(oldItem: operationIcon, newItem: operationIcon): Boolean =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: operationIcon, newItem: operationIcon): Boolean =
            oldItem == newItem
    }
}
