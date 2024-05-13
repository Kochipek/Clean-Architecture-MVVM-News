package com.kochipek.news_app.presentation.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kochipek.news_app.R
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.databinding.FragmentNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val viewModel: NewsDetailsViewModel by viewModels()
    private var isBookmarked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.articleDetails.observe(viewLifecycleOwner) { article ->
            updateUI(article)
        }

        val args = NewsDetailsFragmentArgs.fromBundle(requireArguments())
        val article = args.selectedArticle
        viewModel.fetchNewsDetails(article)

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        enableShareButton()

        viewModel.getSavedNews().observe(viewLifecycleOwner) { savedArticles ->
            isBookmarked = savedArticles.any { it.url == article.url }
            updateBookmarkButton(isBookmarked)
        }

        binding.topAppBar.setOnMenuItemClickListener {
            if (isBookmarked) {
                viewModel.deleteArticle(article)
            } else {
                viewModel.saveArticle(article)
            }
            isBookmarked = !isBookmarked
            updateBookmarkButton(isBookmarked)
        }
    }

    private fun updateUI(article: Article) {
        binding.apply {
            articleTitle.text = article.title
            articleDescription.text = article.description
            // format date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val parsedDate = article.publishedAt?.let { dateFormat.parse(it) }
            val formattedDate = parsedDate?.let {
                SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(
                    it
                )
            }
            date.text = formattedDate
            articleSource.text = article.source?.name
            Glide.with(requireContext())
                .load(article.urlToImage)
                .into(articleImage)

            goToSourceFab.setOnClickListener {
                val action =
                    NewsDetailsFragmentDirections.actionNewsDetailsFragmentToNewsSourceFragment(
                        article.url
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun enableShareButton() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> {
                    shareArticle()
                    true
                }
                R.id.save -> {
                    saveArticle()
                    true
                }
                else -> false
            }
        }
    }

    private fun shareArticle() {
        val article = viewModel.articleDetails.value
        if (article != null) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun saveArticle() {
        val article = viewModel.articleDetails.value
        if (article != null) {
            viewModel.saveArticle(article)
            Snackbar.make(binding.root, "Article saved", Snackbar.LENGTH_LONG).show()
        }
    }


    private fun updateBookmarkButton(isFavorite: Boolean): Boolean {
        if (isFavorite) {
            binding.topAppBar.menu.findItem(R.id.save)?.setIcon(R.drawable.saved_bookmark_24)
        } else {
            binding.topAppBar.menu.findItem(R.id.save)?.setIcon(R.drawable.bookmark_24)
        }
        return true
    }
}
