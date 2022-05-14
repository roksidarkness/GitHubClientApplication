package com.github.clientapplication.feature_github.data.repository

import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository

class LocalRepositoryImp(private val repoDao: RepoDao): LocalRepository {

    override suspend fun getRepos(): List<RepoEntity> {
        return repoDao.getRepos()
    }

    override suspend fun getRepo(id: String): RepoEntity {
        return repoDao.getRepoById(id)
    }

    override suspend fun saveRepos(repos: List<RepoEntity>) {
        val list = repos.toTypedArray()
        repoDao.insertAll(*list)
    }
}