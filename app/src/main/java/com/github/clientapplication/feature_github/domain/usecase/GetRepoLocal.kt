package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.domain.repository.LocalRepository

class GetRepoLocal (
    private val repository: LocalRepository
){
}