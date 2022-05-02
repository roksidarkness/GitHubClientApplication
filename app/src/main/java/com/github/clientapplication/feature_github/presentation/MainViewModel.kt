package com.github.clientapplication.feature_github.presentation

import androidx.lifecycle.ViewModel
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val remoteRepository: RemoteRepository) : ViewModel(){
}