package com.github.clientapplication.feature_github.domain.usecase

data class RepoUseCases(
    val getReposLocal: GetReposLocal,
    val saveRepo: SaveRepoLocal,
    val getReposRemotely: GetReposRemotely,
    val getRepoLocal: GetRepoLocal,
    val addStar: AddStarRemotely
)