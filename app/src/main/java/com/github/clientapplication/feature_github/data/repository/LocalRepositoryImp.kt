package com.github.clientapplication.feature_github.data.repository

import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImp(private val repoDao: RepoDao): LocalRepository {

    override fun getRepos(): Flow<List<RepoEntity>> {
        return repoDao.getRepos()
    }

}