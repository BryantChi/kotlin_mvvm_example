package com.taichung.bryant.kotlin_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttractDetailsViewModel @Inject constructor() : BaseViewModel() {
    private var _go2WebView = MutableLiveData(false)
    val go2WebView: LiveData<Boolean> = _go2WebView

    fun go2WebView() {
        _go2WebView.value = true
    }
}
