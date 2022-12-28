package com.taichung.bryant.kotlin_mvvm.data.attractions

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionsApi {
    @GET("{lang}/Attractions/All")
    suspend fun getAttractionsList(
        @Path("lang") lang: String,
        @Query("page") page: Int
    ): AttractionsResponse
}
