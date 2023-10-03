package com.san.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.san.githubuser.data.local.entity.FavUserEntity
import com.san.githubuser.data.local.room.FavUserDao
import com.san.githubuser.data.remote.response.DetailUserResponse
import com.san.githubuser.data.remote.response.Users
import com.san.githubuser.data.remote.retrofit.ApiService

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val favUserDao: FavUserDao
) {
    fun findUser(username: String): LiveData<Result<List<Users>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items
            if (users == null) emit(Result.Empty)
            else emit(Result.Success(users))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getAllFavUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowings(username: String): LiveData<Result<List<Users>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            if (response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowing: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Result<List<Users>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            if (response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowers: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getDetailUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllFavorite(): LiveData<List<FavUserEntity>> {
        return favUserDao.getAllFavUser()
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return favUserDao.isFavUserExist(username)
    }

    suspend fun addFavorite(user: FavUserEntity) {
        favUserDao.addFavUser(user)
    }

    fun deleteFavorite(username: String) {
        favUserDao.deleteFavUser(username)
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavUserDao
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, favUserDao)
            }.also { instance = it }
    }
}