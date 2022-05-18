package com.github.clientapplication.feature_github.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.clientapplication.feature_github.data.model.AccessToken
import com.github.clientapplication.feature_github.data.pref.PrefManager
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.githubrepos.utils.Constant.CLIENT_ID
import com.github.clientapplication.githubrepos.utils.Constant.CLIENT_SECRET
import com.github.clientapplication.githubrepos.utils.Constant.TAG
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val manager: PrefManager, private val remoteRepository: RemoteRepository) : ViewModel() {

    private val _accessToken = MutableLiveData<AccessToken>()
    val accessToken: LiveData<AccessToken>
        get() = _accessToken

    fun getToken(): String? {
        return manager.currentToken
    }

    private fun saveToken(token: String?){
        manager.currentToken = token
    }

    fun getAccessToken(code: String) {
        viewModelScope.launch{
            try {
                val token: AccessToken = remoteRepository.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
                Log.d(TAG, "Token: ${token.accessToken}")
                saveToken(token.accessToken)
                _accessToken.value = token
            } catch (error: Throwable) {
                Log.d(TAG, "GetAccessToken: error $error")
            }
        }
    }
}