package com.taichung.bryant.kotlin_mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.taichung.bryant.kotlin_mvvm.R
import com.taichung.bryant.kotlin_mvvm.databinding.FragmentAttractionsDetailWebviewBinding

class AttractionsDetailWebviewFragment : Fragment() {

    private lateinit var binding: FragmentAttractionsDetailWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttractionsDetailWebviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        binding.webview.apply {
            webViewClient = WebViewClient()
            loadUrl(url!!)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
        }

        binding.toolbar.setNavigationIcon(R.drawable.arrow_back)

        binding.toolbar.setNavigationOnClickListener { _ ->
            findNavController().popBackStack()
        }
    }
}
