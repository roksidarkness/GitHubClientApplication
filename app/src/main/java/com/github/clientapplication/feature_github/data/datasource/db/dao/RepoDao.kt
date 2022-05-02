package com.github.clientapplication.feature_github.data.datasource.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity


@Dao
interface RepoDao {
    @Query("select * from RepoEntity where id = :id")
    fun getRepoById(id: Long): LiveData<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repos: RepoEntity)

    @Delete
    fun delete(repo: RepoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(repo: RepoEntity)

    @Query("SELECT * FROM repoEntity")
    fun loadAllRepos(): LiveData<List<RepoEntity>>
}