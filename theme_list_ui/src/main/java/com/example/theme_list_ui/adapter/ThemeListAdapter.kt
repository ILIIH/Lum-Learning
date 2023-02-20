package com.example.theme_list_ui.adapter

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.DB.domain.photoLoader
import com.example.theme_list_data.Theme
import com.example.theme_list_ui.databinding.ThemeItemBinding
import com.example.theme_list_ui.themeListNavigation

class ThemeAdapter(
    private val navigator: themeListNavigation,
    private val photoLoader: photoLoader
) : ListAdapter<Theme, ThemeAdapter.ThemeItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeItemViewHolder {
        val view = ThemeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ThemeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ThemeItemViewHolder(
        private val binding: ThemeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(themeItem: Theme) {
            with(binding) {
                title.text = themeItem.Title
                baceImgLayout.setOnClickListener {
                    navigator.toAboutTheme()
                }
                if (themeItem.ImageUri != null) {
                    val bitmap = photoLoader.getPhoto(themeItem.ImageUri!!)
                    val bitmapDr: Drawable = BitmapDrawable(bitmap)
                    baceImgLayout.setBackgroundDrawable(bitmapDr)
                }
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
