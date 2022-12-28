package com.taichung.bryant.kotlin_mvvm.listeners

import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData

interface ItemClickListener {
    fun itemClick(data: AttractionsData)
}