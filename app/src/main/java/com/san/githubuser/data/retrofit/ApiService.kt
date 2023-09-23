package com.san.githubuser.data.retrofit

import com.san.githubuser.data.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.san.githubuser.data.response.GithubResponse
import com.san.githubuser.data.response.Users

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") username: String,
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ) : Call<List<Users>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ) : Call<List<Users>>
}