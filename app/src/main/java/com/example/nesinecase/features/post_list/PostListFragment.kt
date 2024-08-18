package com.example.nesinecase.features.post_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.nesinecase.core.base.BaseFragment
import com.example.nesinecase.databinding.FragmentPostListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment<FragmentPostListBinding>(FragmentPostListBinding::inflate) {

    private val postListViewModel: PostListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postListViewModel.getPosts()
    }
}