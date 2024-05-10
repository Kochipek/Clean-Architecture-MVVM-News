package com.kochipek.news_app.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.databinding.NewsListItemBinding

class NewsFeedAdapter : RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>() {

    private val diffCallback = DiffCallback()
    internal val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(
        private val binding: NewsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvPublisherTime.text = article.publishedAt
                tvSource.text = article.source.name
                Glide.with(imgCover.context)
                    .load(article.urlToImage)
                    .into(imgCover)
            }
        }
    }
}