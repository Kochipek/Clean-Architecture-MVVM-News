package com.kochipek.news_app.presentation.feed

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.kochipek.news_app.R
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.databinding.FragmentNewsFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedFragment : Fragment(R.layout.fragment_news_feed),
    NewsFeedAdapter.NewsItemClickListener {
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsFeedAdapter by lazy { NewsFeedAdapter(this) }
    private lateinit var fragmentNewsFeedBinding: FragmentNewsFeedBinding
    private lateinit var skeleton: Skeleton
    private var page = 1
    private val country = "us"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsFeedBinding = FragmentNewsFeedBinding.bind(view)
        initRecyclerView()
        viewNewsList()
        setSearchedView()
        skeleton =
            fragmentNewsFeedBinding.newsFeedRecyclerView.applySkeleton(R.layout.news_list_item)
        skeleton.showSkeleton()
        lifecycleScope.launch {
            viewModel.getNewsPaging(country).collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
    }

    private fun viewNewsList() {
        viewModel.getNews(country, page)
        viewModel.news.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    skeleton.showSkeleton()
                }

                is Resource.Success -> {
                    onDataLoaded()
                    newsAdapter.differ.submitList(it.data?.articles)
                }

                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "An error occurred: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun setSearchedView() {
        fragmentNewsFeedBinding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.getSearchedNews(query, 1)
                        searchNews()
                    }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if ((query?.length ?: 0) <= 0) viewModel.getNews(country, page)
                    return false
                }
            }
        )

        fragmentNewsFeedBinding.searchView.setOnCloseListener {
            viewNewsList()
            false
        }
    }

    private fun searchNews() {
        viewModel.news.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    skeleton.showSkeleton()
                }

                is Resource.Success -> {
                    onDataLoaded()
                    if (it.data?.articles.isNullOrEmpty()) {
                        fragmentNewsFeedBinding.emptyResultImage.visibility = View.VISIBLE
                        fragmentNewsFeedBinding.newsFeedRecyclerView.visibility = View.GONE
                    } else {
                        fragmentNewsFeedBinding.emptyResultImage.visibility = View.GONE
                        fragmentNewsFeedBinding.newsFeedRecyclerView.visibility = View.VISIBLE
                        newsAdapter.differ.submitList(it.data?.articles)
                    }
                }

                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "An error occurred: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun initRecyclerView() {
        fragmentNewsFeedBinding.newsFeedRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onNewsItemClicked(article: Article) {
        val action = NewsFeedFragmentDirections.actionNewsFeedFragmentToNewsDetailsFragment(article)
        findNavController().navigate(action)
    }

    private fun onDataLoaded() {
        skeleton.showOriginal()
    }
}
