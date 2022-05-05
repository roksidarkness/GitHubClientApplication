package com.github.clientapplication.feature_github.domain.repository

import com.github.clientapplication.feature_github.data.model.Repo
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity

fun Repo.toLocalRepo() = RepoEntity(
    id = id,
    name = name,
    description = description,
    language = language,
    stars = stars
)