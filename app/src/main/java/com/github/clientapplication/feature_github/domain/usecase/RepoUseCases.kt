package com.github.clientapplication.feature_github.domain.usecase

data class RepoUseCases(
    val getRepos: GetRepos,
    val getRepo: GetRepo,
    val addStar: AddStar
)