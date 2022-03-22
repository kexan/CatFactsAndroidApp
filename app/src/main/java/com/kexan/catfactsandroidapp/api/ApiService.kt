package com.kexan.catfactsandroidapp.api

import com.kexan.catfactsandroidapp.dto.Fact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("facts/random?animal_type=cat,dog")
    suspend fun getRandomFact(@Query("amount") count: Int): Response<List<Fact>>

    @GET("facts/{_id}")
    suspend fun getFactById(@Path("_id") _id: String): Response<Fact>
}