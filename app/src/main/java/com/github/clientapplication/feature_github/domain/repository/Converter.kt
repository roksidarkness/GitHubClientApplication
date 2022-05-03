package com.github.clientapplication.feature_github.domain.repository

import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.Repo
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity

fun Repo.toLocalRepo() = RepoEntity(
    id = id,
    name = name
)

fun GetRepositoriesQuery.Node.toRepo() = Repo(
    id = id.toString(),
    name = name.toString()
)