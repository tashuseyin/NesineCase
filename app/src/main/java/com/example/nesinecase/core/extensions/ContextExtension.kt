package com.example.nesinecase.core.extensions

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.nesinecase.R

fun Context.placeholderProgressBar() : CircularProgressDrawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 8f
        centerRadius = 40f
        setColorSchemeColors(R.color.white)
        start()
    }
}