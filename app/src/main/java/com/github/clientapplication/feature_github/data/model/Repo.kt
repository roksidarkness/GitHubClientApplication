package com.github.clientapplication.feature_github.data.model

data class Repo(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int
)