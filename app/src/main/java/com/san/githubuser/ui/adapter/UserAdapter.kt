package com.san.githubuser.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.san.githubuser.databinding.ItemUserBinding
import com.san.githubuser.data.remote.response.Users
import com.san.githubuser.ui.detail.DetailActivity

class UserAdapter(private val onClick: (Users) -> Unit) : ListAdapter<Users, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        Log.d("UserAdapter", "onBindViewHolder: $user")
        holder.bind(user)
    }

    class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.apply {
                binding.tvNama.text = users.login
                Glide.with(binding.root)
                    .load(users.avatarUrl)
                    .into(binding.ivProfilePicture)
            }
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                Log.d("UserAdapter", "onBindViewHolder: $intent")
                intent.putExtra(DetailActivity.EXTRA_LOGIN, users.login)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Users,
                newItem: Users
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}



