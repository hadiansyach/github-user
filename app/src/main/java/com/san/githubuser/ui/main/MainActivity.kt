package com.san.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.R
import com.san.githubuser.databinding.ActivityMainBinding
import com.san.githubuser.ui.data.response.GithubResponse
import com.san.githubuser.ui.data.response.ItemsItem
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

        findUser()
    }

    private fun findUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers("hadiansyach")
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(githubUsers: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(githubUsers)
        binding.rvUserGithub.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}