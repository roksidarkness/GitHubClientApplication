package com.github.clientapplication.feature_github.domain.usecase

data class RepoUseCases(
    val getReposLocal: GetReposLocal,
    val getReposRemotely: GetReposRemotely,
    val getRepoLocal: GetRepoLocal,
    val addStar: AddStarLocal
)