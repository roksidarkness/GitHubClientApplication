package com.github.clientapplication.feature_github.domain.usecase

data class RepoUseCases(
    val getReposLocal: GetReposLocal,
    val saveRepos: SaveReposLocal,
    val getReposRemotely: GetReposRemotely,
    val getRepoLocal: GetRepoLocal,
    val addStar: AddStarRemotely
)