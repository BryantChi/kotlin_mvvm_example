package com.taichung.bryant.kotlin_mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.taichung.bryant.kotlin_mvvm.databinding.FragmentAttractionsDetailWebviewBinding
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseFragment
import com.taichung.bryant.kotlin_mvvm.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionsDetailWebViewFragment :
    BaseFragment<FragmentAttractionsDetailWebviewBinding, MainViewModel>() {

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewBinding() = FragmentAttractionsDetailWebviewBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar(binding.toolbar, bBack = true, action = false) {}
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
    }
}
