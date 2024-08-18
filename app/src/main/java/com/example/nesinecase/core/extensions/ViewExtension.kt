package com.example.nesinecase.core.extensions

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nesinecase.BuildConfig
import com.example.nesinecase.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun ImageView.loadImageUrl(context: Context, imageUrl: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(imageUrl)
        .into(this)
}

fun getImageUrl(itemPosition: Int): String {
    return "${BuildConfig.BASE_IMAGE_URL}&random=$itemPosition"
}


fun Fragment.dialog(
    block: MaterialAlertDialogBuilder.() -> Unit,
) {
    val builder = MaterialAlertDialogBuilder(requireContext())
    block.invoke(builder)
    builder.show()
}

fun Fragment.errorDialog(
    block: MaterialAlertDialogBuilder.() -> Unit,
) {
    this.dialog {
        setTitle(this.context.getString(R.string.app_name))
        setCancelable(true)
        setPositiveButton(this.context.getString(R.string.app_name)) { _, _ -> }
        block.invoke(this)
    }
}


