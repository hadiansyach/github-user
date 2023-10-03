package com.san.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.san.githubuser.R
import com.san.githubuser.data.local.entity.FavUserEntity
import com.san.githubuser.databinding.ActivityDetailBinding
import com.san.githubuser.ui.adapter.SectionsPagerAdapter
import com.san.githubuser.ui.favorite.FavoriteActivity
import com.san.githubuser.ui.setting.SettingActivity
import com.san.githubuser.ui.viewmodel.DetailViewModel
import com.san.githubuser.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var user: FavUserEntity = FavUserEntity(0, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_LOGIN)

        supportActionBar?.title = "Detail User"

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        detailViewModel.getDetailUser(username.toString())
        detailViewModel.favUserEntity.observe(this) {
            user = it
        }

        detailViewModel.detailUser.observe(this) {
            with(binding) {
                progressBar.isGone = true
                if (it != null) {
                    tvUsername.text = it.login
                    tvNama.text = it.name
                    jumlahFollowers.text = it.followers.toString()
                    jumlahFollowing.text = it.following.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.ivProfilePicture)
                        .clearOnDetach()
                }
            }
        }

        detailViewModel.isFavorite(username.toString()).observe(this) {
            if (it != null) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.deleteFavorite(username.toString())
                }
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
                binding.fabFavorite.setOnClickListener {
                    detailViewModel.addFavorite(user)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteUser -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_LOGIN = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}

