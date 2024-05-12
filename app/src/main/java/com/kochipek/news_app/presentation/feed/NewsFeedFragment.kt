package com.kochipek.news_app.presentation.feed

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kochipek.news_app.R
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.databinding.FragmentNewsFeedBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsFeedAdapter by lazy { NewsFeedAdapter() }
    private lateinit var fragmentNewsFeedBinding: FragmentNewsFeedBinding
    private var country = "us"
    private var page = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsFeedBinding = FragmentNewsFeedBinding.bind(view)
        initRecyclerView()
        viewNewsList()
        setSearchedView()
    }

    private fun viewNewsList() {
        viewModel.getNews(country, page)
        viewModel.news.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(it.data?.articles)
                }

                is Resource.Error -> {
                    hideProgressBar()
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
                    if (query != null) {
                        viewModel.getSearchedNews(query, 1)
                        searchNews()
                    }
                    return false
                }
            }
        )

        fragmentNewsFeedBinding.searchView.setOnCloseListener {
            // initRecyclerView()
            viewNewsList()
            false
        }
    }

    private fun searchNews() {
        viewModel.searchedNews.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(it.data?.articles)
                }

                is Resource.Error -> {
                    hideProgressBar()
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

    private fun showProgressBar() {
        fragmentNewsFeedBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        fragmentNewsFeedBinding.progressBar.visibility = View.GONE
    }
}
