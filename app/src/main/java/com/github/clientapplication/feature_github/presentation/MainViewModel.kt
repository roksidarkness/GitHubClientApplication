package com.github.clientapplication.feature_github.presentation

import androidx.lifecycle.ViewModel
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repoUseCases: RepoUseCases) : ViewModel(){
}