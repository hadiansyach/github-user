package com.san.githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.githubuser.data.GithubUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    fun getAllFavorite() = githubUserRepository.getAllFavorite()
    fun deleteAllFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }
}