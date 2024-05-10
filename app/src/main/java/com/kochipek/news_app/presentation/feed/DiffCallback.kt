package com.kochipek.news_app.presentation.feed

import androidx.recyclerview.widget.DiffUtil
import com.kochipek.news_app.data.model.Article

class DiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}