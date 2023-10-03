package com.san.githubuser.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.databinding.ActivityMainBinding
import com.san.githubuser.data.remote.response.Users
import com.san.githubuser.ui.adapter.UserAdapter
import com.san.githubuser.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
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
//            TODO: Setup menu
//            searchBar.inflateMenu(R.menu.option_menu)
//            searchBar.setOnMenuItemClickListener {
//                when (it.itemId) {
//                    R.id.setting -> {
//                        val action = Intent(this@MainActivity, DetailActivity::class.java)
//                        startActivity(action)
//                    }
//                }
//                true
//            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserGithub.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        mainViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }



    }

    private fun setUserData(githubUsers: List<Users>) {
        val adapter = UserAdapter()
        adapter.submitList(githubUsers)
        binding.rvUserGithub.adapter = adapter
        Log.d(TAG, "setUserData: dapat dijalankan")
    }
}