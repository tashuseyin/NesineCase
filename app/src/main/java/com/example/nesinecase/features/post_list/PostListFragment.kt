package com.example.nesinecase.features.post_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.nesinecase.core.base.BaseFragment
import com.example.nesinecase.databinding.FragmentPostListBinding
import com.example.nesinecase.features.post_list.adapter.PostListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>(FragmentPostListBinding::inflate) {

    private val postListViewModel: PostListViewModel by viewModels()
    private val postListAdapter by lazy { PostListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        collectViewModel()
    }

    private fun setUpRecyclerView() {
        binding.postListRecyclerview.addItemDecoration(DividerItemDecoration(binding.postListRecyclerview.context, DividerItemDecoration.VERTICAL))
        binding.postListRecyclerview.adapter = postListAdapter
    }

    private fun collectViewModel() {
        lifecycleScope.launch {
            postListViewModel.posts.collect { posts ->
                if (posts.isNotEmpty()) {
                    postListAdapter.submitList(posts)
                }
            }
        }
    }
}