package com.kochipek.news_app.presentation.savedNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kochipek.news_app.data.model.Article
import com.kochipek.news_app.databinding.FragmentSavedNewsBinding
import com.kochipek.news_app.presentation.feed.NewsFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(), NewsFeedAdapter.NewsItemClickListener {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: SavedNewsViewModel by viewModels()
    private val savedNewsAdapter: NewsFeedAdapter by lazy { NewsFeedAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        binding.savedNewsFeedRecyclerView.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Initialize ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No need to handle move action
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = savedNewsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Article deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.savedNewsFeedRecyclerView)
    }

    private fun observeViewModel() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            savedNewsAdapter.differ.submitList(articles)
            if (articles.isEmpty()) {
                binding.emptyResultLayout.visibility = View.VISIBLE
            } else {
                binding.emptyResultLayout.visibility = View.GONE
            }
        }
    }

    override fun onNewsItemClicked(article: Article) {
        val action =
            SavedNewsFragmentDirections.actionSavedNewsFragmentToNewsDetailsFragment(article)
        findNavController().navigate(action)
    }
}
