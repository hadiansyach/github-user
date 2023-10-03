package com.san.githubuser.data.di

import android.content.Context
import com.san.githubuser.data.GithubUserRepository
import com.san.githubuser.data.local.room.FavUserDatabase
import com.san.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideInjection(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()
        return GithubUserRepository.getInstance(apiService, dao)
    }
}
