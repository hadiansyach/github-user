package com.san.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.san.githubuser.data.local.entity.FavUserEntity
import com.san.githubuser.databinding.FavItemUserBinding

class FavoriteUserAdapter(
    private val onClick: (FavUserEntity) -> Unit,
    private val onDelete: (FavUserEntity) -> Unit
) : ListAdapter<FavUserEntity, FavoriteUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FavItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClick(user)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(user)
        }
    }

    class MyViewHolder(val binding: FavItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUserEntity) {
            binding.tvNamaUser.text = user.username
            binding.btnDelete.isVisible = true
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.ivProfilePicture)
                .clearOnDetach()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavUserEntity>() {
            override fun areItemsTheSame(oldItem: FavUserEntity, newItem: FavUserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavUserEntity,
                newItem: FavUserEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}