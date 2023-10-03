package com.san.githubuser.ui.favorite

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.san.githubuser.databinding.ActivityFavoriteBinding
import com.san.githubuser.ui.adapter.FavoriteUserAdapter

class FavoriteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}