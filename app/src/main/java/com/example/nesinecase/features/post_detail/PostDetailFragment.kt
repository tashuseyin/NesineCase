package com.example.nesinecase.features.post_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nesinecase.core.base.BaseFragment
import com.example.nesinecase.core.extensions.loadImageUrl
import com.example.nesinecase.core.extensions.placeholderProgressBar
import com.example.nesinecase.databinding.FragmentPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::inflate) {

    private val args: PostDetailFragmentArgs by navArgs<PostDetailFragmentArgs>()
    private val postDetailViewModel: PostDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        collectViewModel()
    }

    private fun collectViewModel() {
        lifecycleScope.launch {
            postDetailViewModel.isUpdatePost.collect { bool ->
                bool?.let {
                    if (it) {
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "TRUE", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "FALSE", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setUI() {
        binding.apply {
            postImage2.loadImageUrl(
                requireContext(),
                args.imageUrl,
                requireContext().placeholderProgressBar()
            )
            body.setText(args.postUIModel.body)
            title.setText(args.postUIModel.title)

            updateButton.setOnClickListener {
                postDetailViewModel.updatePost(args.postUIModel.copy(body = body.text.toString(), title = title.text.toString()))
            }
        }
    }
}
