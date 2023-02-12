package com.example.theme_list_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theme_list_data.Theme
import com.example.theme_list_ui.R
import com.example.theme_list_ui.databinding.ThemeItemBinding
import com.example.theme_list_ui.themeListNavigation

class ProfileAdapter(
    private val navigator: themeListNavigation
) : ListAdapter<Theme, ProfileAdapter.ThemeItemViewHolder>(DiffCallBack()) {

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
                // TO_DO baceImgLayout
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
