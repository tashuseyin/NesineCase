package com.example.nesinecase.core.extensions

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nesinecase.BuildConfig
import com.example.nesinecase.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun ImageView.loadImageUrl(context: Context, imageUrl: String?) {
    val options = RequestOptions()
        .placeholder(context.placeholderProgressBar())
        .error(R.drawable.ic_image_placeholder)


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(imageUrl)
        .into(this)
}

fun getImageUrl(itemPosition: Int): String {
    return "${BuildConfig.BASE_IMAGE_URL}&random=$itemPosition"
}

fun Context.placeholderProgressBar() : CircularProgressDrawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 8f
        centerRadius = 40f
        setColorSchemeColors(R.color.white)
        start()
    }
}

fun Context.showDialog(
    title: String? = null,
    message: String? = null,
    cancelable: Boolean = true,
    positiveButtonText: String? = null,
    onPositiveButtonClick: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClick: (() -> Unit)? = null,
    block: MaterialAlertDialogBuilder.() -> Unit = {}
) {
    val builder = MaterialAlertDialogBuilder(this).apply {
        title?.let { setTitle(it) }
        message?.let { setMessage(it) }
        setCancelable(cancelable)
        setOnDismissListener { onNegativeButtonClick?.invoke() }
        positiveButtonText?.let {
            setPositiveButton(it) { _, _ -> onPositiveButtonClick?.invoke() }
        }
        negativeButtonText?.let {
            setNegativeButton(it){ _, _ -> onNegativeButtonClick?.invoke() }
        }
        block.invoke(this)
    }
    builder.show()
}