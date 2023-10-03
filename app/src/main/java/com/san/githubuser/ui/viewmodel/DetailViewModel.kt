package com.san.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.githubuser.data.GithubUserRepository
import com.san.githubuser.data.Result
import com.san.githubuser.data.local.entity.FavUserEntity
import com.san.githubuser.data.remote.response.DetailUserResponse
import com.san.githubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    private val _userEntity = MutableLiveData<FavUserEntity>()
    val favUserEntity: LiveData<FavUserEntity> = _userEntity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: MutableLiveData<DetailUserResponse?> = _detailUser

    fun addFavorite(user: FavUserEntity) {
        viewModelScope.launch {
            githubUserRepository.addFavorite(user)
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }

    fun isFavorite(username: String) = githubUserRepository.isFavorite(username)

    fun getDetailUser(username: String) {
        githubUserRepository.getDetailUser(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _detailUser.value = result.data
                    _userEntity.value = FavUserEntity(0, result.data.login, result.data.avatarUrl)
                }

                is Result.Error -> {
                    _isLoading.value = false
                }

                is Result.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }
}


