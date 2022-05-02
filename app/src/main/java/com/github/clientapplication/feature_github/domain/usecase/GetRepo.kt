package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.domain.repository.RemoteRepository

class GetRepo (
    private val repository: RemoteRepository
){
}