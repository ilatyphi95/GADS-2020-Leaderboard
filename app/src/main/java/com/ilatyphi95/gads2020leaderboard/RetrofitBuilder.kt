package com.ilatyphi95.gads2020leaderboard

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

object RetrofitBuilder {

    private const val GET_BASE_URL = "https://gadsapi.herokuapp.com"

    private const val POST_BASE_URL = ""

    private fun getRetrofit(baseUrl: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val getService: LeaderService =
        getRetrofit(GET_BASE_URL).create(LeaderService::class.java)

    val postService =
        getRetrofit(POST_BASE_URL).create(PostService::class.java)
}

interface LeaderService {
    @GET("api/skilliq")
    suspend fun getSkillLeader() : List<IQLeader>

    @GET("api/hours")
    suspend fun getLearningLeader() : List<LearningLeader>
}

interface PostService {
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    fun postProject(@Field("entry.1877115667")firstName:String,
                    @Field("entry.2006916086")lastName:String,
                    @Field("entry.1824927963")email:String,
                    @Field("entry.284483984")projectLink:String) : Call<Void>
}
