package com.san.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.databinding.ActivityFavoriteBinding
import com.san.githubuser.ui.adapter.FavoriteUserAdapter
import com.san.githubuser.ui.detail.DetailActivity
import com.san.githubuser.ui.viewmodel.FavoriteViewModel
import com.san.githubuser.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("FAVORITE ACTIVITY", "onCreate: Favorite Screen")

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager

        adapter = FavoriteUserAdapter({
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.username)
            startActivity(intent)
        }, {
            favoriteViewModel.deleteAllFavorite(it.username.toString())
            favoriteViewModel.getAllFavorite().observe(this) { result ->
                adapter.submitList(result)
            }
        })

        binding.rvUserGithub.adapter = adapter
        favoriteViewModel.getAllFavorite().observe(this){result ->
            binding.progressBar.isGone = true
            adapter.submitList(result)
            if(result.isEmpty()){
                binding.tvEmpty.isVisible = true
            }
        }
    }
}