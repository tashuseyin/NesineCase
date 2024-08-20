package com.example.nesinecase.features.post_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nesinecase.R
import com.example.nesinecase.core.base.BaseFragment
import com.example.nesinecase.core.extensions.getDataCurrentScreen
import com.example.nesinecase.core.extensions.showDialog
import com.example.nesinecase.core.util.Constants
import com.example.nesinecase.core.util.ProgressDialogUtil
import com.example.nesinecase.core.util.SwipeToDelete
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
        checkPostUpdate()
        setUpRecyclerView()
        collectViewModel()
    }

    private fun checkPostUpdate() {
        findNavController().getDataCurrentScreen<Boolean>(Constants.IS_UPDATE_POST_KEY)?.let {
            if (it) {
                postListViewModel.getAllPostsFromDB()
            }
        }
    }


    private fun setUpRecyclerView() {
        binding.postListRecyclerview.adapter = postListAdapter
        binding.postListRecyclerview.addItemDecoration(
            DividerItemDecoration(
                binding.postListRecyclerview.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val swipeToDelete = object : SwipeToDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                requireContext().showDialog(
                    title = getString(R.string.delete_post),
                    message = getString(R.string.are_you_sure_you_want_to_remove_post),
                    positiveButtonText = getString(R.string.yes),
                    negativeButtonText = getString(R.string.no),
                    onPositiveButtonClick = {
                        val postItem = postListAdapter.currentList[viewHolder.adapterPosition]
                        postListViewModel.deletePost(postItem)
                    },
                    onNegativeButtonClick = {
                        postListAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding.postListRecyclerview)
    }

    private fun collectViewModel() {
        lifecycleScope.launch {
            postListViewModel.uiState.collect { state ->
                ProgressDialogUtil.checkProgressDialog(requireContext(), state.isLoading)
                if (state.posts.isNotEmpty()) {
                    postListAdapter.submitList(state.posts)
                }
                if (state.isError) {
                    requireContext().showDialog(
                        title = getString(R.string.an_error_occurred),
                        message = state.errorMessage
                    )
                }
            }
        }
    }
}
