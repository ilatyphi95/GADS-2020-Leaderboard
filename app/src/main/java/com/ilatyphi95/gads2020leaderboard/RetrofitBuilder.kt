package com.ilatyphi95.gads2020leaderboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitBuilder {

    private const val BASE_URL = "https://gadsapi.herokuapp.com"

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: LeaderService =
        getRetrofit().create(LeaderService::class.java)
}

interface LeaderService {
    @GET("api/skilliq")
    suspend fun getSkillLeader() : List<IQLeader>

    @GET("api/hours")
    suspend fun getLearningLeader() : List<LearningLeader>
}
