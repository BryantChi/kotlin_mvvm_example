package com.taichung.bryant.kotlin_mvvm.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
}
