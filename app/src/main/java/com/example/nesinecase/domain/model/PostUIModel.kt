package com.example.nesinecase.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class PostUIModel(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable