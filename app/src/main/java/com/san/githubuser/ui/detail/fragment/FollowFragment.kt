package com.san.githubuser.ui.detail.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.san.githubuser.databinding.FragmentFollowBinding
import com.san.githubuser.ui.detail.DetailActivity
import com.san.githubuser.ui.adapter.UserAdapter
import com.san.githubuser.ui.viewmodel.FollowViewModel
import com.san.githubuser.ui.viewmodel.ViewModelFactory

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private val followViewModel: FollowViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_POSITION, 0)
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = layoutManager

        adapter = UserAdapter {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        binding.rvFollow.adapter = adapter
        followViewModel.isEmpty.observe(viewLifecycleOwner) {
            binding.tvEmpty.visibility = if (it) View.VISIBLE else View.GONE
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        followViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        if (index == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            followViewModel.getFollowings(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }
}