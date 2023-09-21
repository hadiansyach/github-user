package com.san.githubuser.ui.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.san.githubuser.ui.data.response.GithubResponse as GithubResponse1

interface ApiService {
    @GET("users")
    fun getUsers(
        @Path("username") username: String
    ): Call<GithubResponse1>
}