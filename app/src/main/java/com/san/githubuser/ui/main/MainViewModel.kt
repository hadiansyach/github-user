package com.san.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.san.githubuser.data.response.GithubResponse
import com.san.githubuser.data.response.Users
import com.san.githubuser.data.retrofit.ApiConfig
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<Users>>()
    val listUser: LiveData<List<Users>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("ihsan")
    }

    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: retrofit2.Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "onResponse: ${response.body()?.items}")
                    _isEmpty.value = response.body()?.items!!.isEmpty()
                    _listUser.value = response.body()?.items
                } else {
                    _errorMessage.value = "Gagal mengambil data: ${response.message()}"
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Terjadi kesalahan jaringan: ${t.message.toString()}"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}