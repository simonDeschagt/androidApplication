package com.example.fitysalud

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.example.fitysalud.databinding.FragmentEducativaBinding

class Educativa : Fragment() {

    private lateinit var binding: FragmentEducativaBinding
    private var isFullSize = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEducativaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView(binding.articulo1, "https://www.who.int/es/news-room/q-a-detail/coronavirus-disease-covid-19")
        setupWebView(binding.articulo2, "https://www.mayoclinic.org")
        setupWebView(binding.articulo3, "https://www.cdc.gov")

        setupButton(binding.fullSizeButton1, binding.articulo1)
        setupButton(binding.fullSizeButton2, binding.articulo2)
        setupButton(binding.fullSizeButton3, binding.articulo3)
    }

    private fun setupWebView(webView: WebView, url: String) {
        webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }

    private fun setupButton(button: View, webView: WebView) {
        button.setOnClickListener {
            if (isFullSize) {
                webView.layoutParams.height = 300.dpToPx()
                (button as Button).text = "Full Size"
            } else {
                webView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                (button as Button).text = "Shrink"
            }
            webView.requestLayout()
            isFullSize = !isFullSize
        }
    }

    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}