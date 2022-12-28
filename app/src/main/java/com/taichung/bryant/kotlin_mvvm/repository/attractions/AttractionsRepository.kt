package com.taichung.bryant.kotlin_mvvm.repository.attractions

import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsApi
import javax.inject.Inject

class AttractionsRepository @Inject constructor(
    private val api: AttractionsApi
) {
    suspend fun getAttractionsList(lang: String, page: Int) = api.getAttractionsList(lang, page)
}
