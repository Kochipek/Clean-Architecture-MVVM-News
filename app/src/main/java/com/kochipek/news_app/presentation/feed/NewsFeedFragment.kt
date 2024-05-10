package com.kochipek.news_app.presentation.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kochipek.news_app.R
import com.kochipek.news_app.data.util.Resource
import com.kochipek.news_app.databinding.FragmentNewsFeedBinding
import com.kochipek.news_app.presentation.MainActivity
import com.kochipek.news_app.presentation.viewmodel.NewsViewModel

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsFeedAdapter
    private lateinit var fragmentNewsFeedBinding: FragmentNewsFeedBinding
    private var country = "us"
    private var page = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsFeedBinding = FragmentNewsFeedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        viewNewsList()
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
                            "An error occured: $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsFeedAdapter()
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