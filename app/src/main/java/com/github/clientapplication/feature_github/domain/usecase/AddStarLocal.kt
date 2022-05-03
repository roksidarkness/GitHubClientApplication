package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository

class AddStarLocal (
    private val repository: LocalRepository
){
}