package com.kochipek.news_app.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kochipek.news_app.R
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.databinding.NewsListItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NewsFeedAdapter(private val listener: NewsItemClickListener) :
    PagingDataAdapter<Article, NewsFeedAdapter.NewsViewHolder>(DiffCallback()) {

    private val diffCallback = DiffCallback()
    internal val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList.getOrNull(position)
        article?.let { holder.bind(it) }
    }
    interface NewsItemClickListener {
        fun onNewsItemClicked(article: Article)
    }
    override fun getItemCount(): Int = differ.currentList.size

    inner class NewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(binding) {
                tvTitle.text = article.title.orEmpty()
                tvPublisherTime.text = formatDate(article.publishedAt)
                tvSource.text = article.source?.name.orEmpty()
                Glide.with(imgCover.context)
                    .load(article.urlToImage)
                    .error(R.drawable.no_image_found)
                    .into(imgCover)

                itemView.setOnClickListener {
                    val article = differ.currentList[bindingAdapterPosition]
                    listener.onNewsItemClicked(article)
                }
            }
        }
        private fun formatDate(dateString: String?): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val parsedDate = dateString?.let { dateFormat.parse(it) }
            val formattedDate =
                parsedDate?.let { SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(it) }
            return formattedDate.orEmpty()
        }
    }
}
