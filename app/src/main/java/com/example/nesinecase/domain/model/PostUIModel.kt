package com.example.nesinecase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostUIModel(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable