package com.san.githubuser.data.remote.retrofit

import com.san.githubuser.data.remote.response.DetailUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.san.githubuser.data.remote.response.GithubResponse
import com.san.githubuser.data.remote.response.Users

interface ApiService {

    @GET("search/users")
    suspend fun getUsers(
        @Query("q") username: String,
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
    ) : List<Users>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
    ) : List<Users>
}