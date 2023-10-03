package com.san.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.githubuser.data.GithubUserRepository
import com.san.githubuser.data.SettingPreference
import com.san.githubuser.data.dataStore
import com.san.githubuser.data.di.Injection

class ViewModelFactory private constructor(
    private val preference: SettingPreference, private val githubUserRepository: GithubUserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java -> MainViewModel(preference, githubUserRepository)
        DetailViewModel::class.java -> DetailViewModel(githubUserRepository)
        FavoriteViewModel::class.java -> FavoriteViewModel(githubUserRepository)
        FollowViewModel::class.java -> FollowViewModel(githubUserRepository)
        SettingViewModel::class.java -> SettingViewModel(preference)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    SettingPreference.getInstance(application.dataStore),
                    Injection.provideInjection(application.applicationContext)
                )
            }.also { instance = it }
    }
}