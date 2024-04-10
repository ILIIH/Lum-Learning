package com.lum.formula_builder_ui.adapters
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lum.formula_builder_ui.databinding.BigOperatorItemBinding
import com.lum.formula_builder_ui.databinding.SmallOperatorItemBinding
import com.lum.formula_builder_ui.operationIcon

class OperatorIconAdapter(
    private val drawIcon: (img: Bitmap) -> Unit
) :
    ListAdapter<operationIcon, OperatorIconAdapter.OperatorIconViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperatorIconViewHolder {
        val view = SmallOperatorItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OperatorIconViewHolder(view)
    }

    override fun onBindViewHolder(holder: OperatorIconViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class OperatorIconViewHolder(
        private val binding: SmallOperatorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(operationItem: operationIcon) {
            with(binding) {
                sumbol.setImageBitmap(operationItem.image)
                operatorBackground.setOnClickListener {
                    drawIcon(operationItem.image)
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
