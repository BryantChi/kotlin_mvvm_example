package com.taichung.bryant.kotlin_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttractDetailsViewModel @Inject constructor() : BaseViewModel() {
    private var _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    fun go2Webview(url: String) {
        _url.postValue(url)
    }
}
