package com.san.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.R
import com.san.githubuser.databinding.ActivityMainBinding
import com.san.githubuser.ui.adapter.UserAdapter
import com.san.githubuser.ui.detail.DetailActivity
import com.san.githubuser.ui.favorite.FavoriteActivity
import com.san.githubuser.ui.viewmodel.MainViewModel
import com.san.githubuser.ui.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        binding.rvUserGithub.adapter = adapter

        mainViewModel.isEmpty.observe(this) {
            binding.tvEmpty.isVisible = it
        }

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        mainViewModel.listUser.observe(this) {
            adapter.submitList(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    mainViewModel.findUser(searchView.text.toString())
                    searchView.hide()
                    true
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favoriteUser -> {
                        val action = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(action)
                    }
                    R.id.setting -> {
                        Toast.makeText(this@MainActivity, "Setting", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserGithub.addItemDecoration(itemDecoration)
    }
}