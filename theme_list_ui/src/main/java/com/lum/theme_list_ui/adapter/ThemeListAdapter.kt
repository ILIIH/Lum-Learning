package com.lum.theme_list_ui.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lum.theme_list_data.Theme
import com.lum.theme_list_ui.databinding.ThemeItemBinding
import com.lum.theme_list_ui.ThemeListNavigation

class ThemeAdapter(
    private val navigator: ThemeListNavigation,
) : ListAdapter<Theme, ThemeAdapter.ThemeItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeItemViewHolder {
        val view = ThemeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return ThemeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ThemeItemViewHolder(
        private val binding: ThemeItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(themeItem: Theme) {
            with(binding) {
                title.text = themeItem.Title
                baceImgLayout.setOnClickListener {
                    navigator.toAboutTheme(themeItem.id)
                }

                val bitmap = BitmapFactory.decodeByteArray(themeItem.photo, 0, themeItem.photo.size)
                val bitmapDr: Drawable = BitmapDrawable(binding.root.context.resources , bitmap)
                baceImgLayout.background = bitmapDr
            }
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Theme>() {
        override fun areItemsTheSame(oldItem: Theme, newItem: Theme): Boolean =
            oldItem.Title == newItem.Title

        override fun areContentsTheSame(oldItem: Theme, newItem: Theme): Boolean =
            oldItem == newItem
    }
}
