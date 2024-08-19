package com.example.nesinecase.features.post_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nesinecase.core.base.BaseFragment
import com.example.nesinecase.core.extensions.loadImageUrl
import com.example.nesinecase.core.extensions.setDataPreviousScreen
import com.example.nesinecase.core.util.Constants
import com.example.nesinecase.databinding.FragmentPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val args: PostDetailFragmentArgs by navArgs<PostDetailFragmentArgs>()
    private val postDetailViewModel: PostDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUIView()
        onUpdateClickListener()
        collectViewModel()
    }

    private fun collectViewModel() {
        lifecycleScope.launch {
            postDetailViewModel.isUpdatePost.collect { isUpdate ->
                isUpdate?.let {
                    findNavController().setDataPreviousScreen(Constants.IS_UPDATE_POST_KEY, isUpdate)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun onUpdateClickListener() {
        binding.updateButton.setOnClickListener {
            postDetailViewModel.updatePost(
                args.postUIModel.copy(body = binding.body.text.toString(), title = binding.title.text.toString())
            )
        }
    }

    private fun setUIView() {
        binding.apply {
            postImage.loadImageUrl(requireContext(), args.imageUrl)
            body.setText(args.postUIModel.body)
            title.setText(args.postUIModel.title)
        }
    }
}
