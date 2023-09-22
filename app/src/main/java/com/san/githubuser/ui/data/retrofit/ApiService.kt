package com.san.githubuser.ui.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.san.githubuser.ui.data.response.GithubResponse
import com.san.githubuser.ui.data.response.Users
import retrofit2.http.Headers

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String,
    ): Call<GithubResponse>

}