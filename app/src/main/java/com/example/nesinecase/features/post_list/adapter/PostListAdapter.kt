package com.example.nesinecase.features.post_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nesinecase.core.extensions.getImageUrl
import com.example.nesinecase.core.extensions.loadImageUrl
import com.example.nesinecase.databinding.LayoutPostItemBinding
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.features.post_list.PostListFragmentDirections

class PostListAdapter : ListAdapter<PostUIModel,PostListViewHolder>(PostsDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = LayoutPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(currentList[position], itemPosition = position)
    }
}


class PostListViewHolder(private val binding: LayoutPostItemBinding): ViewHolder(binding.root) {
    fun bind(postItem: PostUIModel, itemPosition: Int) {
        val imageUrl = getImageUrl(itemPosition = itemPosition)
        binding.apply {
            postImage.loadImageUrl(postImage.context, imageUrl)
            title.text = postItem.title
            body.text = postItem.body
            root.setOnClickListener {
                it.findNavController().navigate(PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(postItem, imageUrl))
            }
        }
    }
}


class PostsDiffUtilCallBack : DiffUtil.ItemCallback<PostUIModel>() {

    override fun areItemsTheSame(oldItem: PostUIModel, newItem: PostUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostUIModel, newItem: PostUIModel): Boolean {
        return oldItem.body == newItem.body && oldItem.title == newItem.title
    }
}
