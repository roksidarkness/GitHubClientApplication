package com.github.clientapplication.feature_github.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.githubrepos.utils.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow

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