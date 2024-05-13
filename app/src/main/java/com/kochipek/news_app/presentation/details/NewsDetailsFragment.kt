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
class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val viewModel: NewsDetailsViewModel by viewModels()
    private lateinit var article: Article
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
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> {
                    shareArticle()
                    true
                }

                R.id.save -> {
                    if (isBookmarked) {
                        viewModel.deleteArticle(article)
                    } else {
                        viewModel.saveArticle(article)
                        Snackbar.make(binding.root, "Article saved", Snackbar.LENGTH_SHORT).show()
                    }
                    isBookmarked = !isBookmarked
                    updateBookmarkButton(isBookmarked)
                    true
                }

                else -> false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.articleDetails.observe(viewLifecycleOwner) { article ->
            this.article = article
            updateUI(article)
            viewModel.getSavedNews().observe(viewLifecycleOwner) { savedArticles ->
                isBookmarked = savedArticles.any { it.url == article.url }
                updateBookmarkButton(isBookmarked)
            }
        }

        val args = NewsDetailsFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchNewsDetails(args.selectedArticle)
    }

    private fun updateUI(article: Article) {
        binding.apply {
            articleTitle.text = article.title
            articleDescription.text = article.description
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val parsedDate = article.publishedAt?.let { dateFormat.parse(it) }
            val formattedDate = parsedDate?.let {
                SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(it)
            }
            date.text = formattedDate
            articleSource.text = article.source?.name
            Glide.with(requireContext())
                .load(article.urlToImage)
                .into(articleImage)

            goToSourceFab.setOnClickListener {
                val action = NewsDetailsFragmentDirections
                    .actionNewsDetailsFragmentToNewsSourceFragment(article.url)
                findNavController().navigate(action)
            }
        }
    }

    private fun shareArticle() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, article.url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun updateBookmarkButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.topAppBar.menu.findItem(R.id.save)?.setIcon(R.drawable.saved_bookmark_24)
        } else {
            binding.topAppBar.menu.findItem(R.id.save)?.setIcon(R.drawable.bookmark_24)
        }
    }
}
