package com.san.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.R
import com.san.githubuser.databinding.ActivityMainBinding
import com.san.githubuser.ui.data.response.GithubResponse
import com.san.githubuser.ui.data.response.Users
import com.san.githubuser.ui.data.retrofit.ApiConfig
import com.san.githubuser.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    true
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.setting -> {
                        val action = Intent(this@MainActivity, DetailActivity::class.java)
                        startActivity(action)
                    }
                }
                true
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserGithub.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setUserData(githubUsers: List<Users>) {
        val adapter = UserAdapter()
        adapter.submitList(githubUsers)
        binding.rvUserGithub.adapter = adapter
        Log.d(TAG, "setUserData: dapat dijalankan")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}