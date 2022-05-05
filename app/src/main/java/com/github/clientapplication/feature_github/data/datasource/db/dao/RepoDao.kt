package com.github.clientapplication.feature_github.data.datasource.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity

@Dao
interface RepoDao {
    @Query("select * from RepoEntity where id = :id")
    suspend fun getRepoById(id: String): RepoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg repos: RepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RepoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(repo: RepoEntity)

    @Query("SELECT * FROM repoEntity")
    fun loadAllRepos(): LiveData<List<RepoEntity>>

    @Query("SELECT * FROM repoEntity")
    suspend fun getRepos(): List<RepoEntity>
}