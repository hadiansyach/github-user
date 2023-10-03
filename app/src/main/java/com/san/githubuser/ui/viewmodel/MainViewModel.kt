package com.san.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.san.githubuser.data.GithubUserRepository
import com.san.githubuser.data.Result
import com.san.githubuser.data.SettingPreference
import com.san.githubuser.data.remote.response.Users


class MainViewModel(
    private val preference: SettingPreference,
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {
    private val _listUser = MutableLiveData<List<Users>?>()
    val listUser: LiveData<List<Users>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        findUser("ihsan")
    }

    fun findUser(username: String) {
        githubUserRepository.findUser(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listUser.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }

                is Result.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }
        }
    }
    fun getThemeSetting(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }
}