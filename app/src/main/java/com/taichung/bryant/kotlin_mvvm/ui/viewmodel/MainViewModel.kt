package com.taichung.bryant.kotlin_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData
import com.taichung.bryant.kotlin_mvvm.repository.attractions.AttractionsRepository
import com.taichung.bryant.kotlin_mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val attractionsRepository: AttractionsRepository
) : BaseViewModel() {
    private var _attractonsList = MutableLiveData<List<AttractionsData>>()
    val attractionsList: LiveData<List<AttractionsData>> = _attractonsList

    private var _page = MutableLiveData<Int>(1)
    val page: LiveData<Int> = _page

    fun getAttractionsAll(lang: String, page: Int) {
        isLoading.postValue(true)
        viewModelScope.launch {
            attractionsRepository.getAttractionsList(lang, page).let { result ->
                _attractonsList.value = result.data
                isLoading.postValue(false)
            }
        }
    }

    fun getNextPage() {
        _page.value?.let {
            this._page.value = it.plus(1)
        }
    }

    fun clearData() {
        _attractonsList.postValue(emptyList())
        _page.postValue(1)
    }
}
