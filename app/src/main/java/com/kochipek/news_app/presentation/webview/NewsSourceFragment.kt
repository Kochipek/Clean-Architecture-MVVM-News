package com.kochipek.news_app.presentation.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kochipek.news_app.R
import com.kochipek.news_app.databinding.FragmentNewsSourceBinding

class NewsSourceFragment : Fragment(R.layout.fragment_news_source) {
    private lateinit var binding: FragmentNewsSourceBinding
    private val args: NewsSourceFragmentArgs by navArgs()
    private lateinit var webView: WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.newsSourceWebView
        args.url?.let { loadWebsite(it) }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        webViewSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.destroy()
    }

    private fun loadWebsite(url: String) {
        webView.loadUrl(url)
    }

    private fun webViewSettings() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.useWideViewPort = true
    }
}
