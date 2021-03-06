package com.github.clientapplication.feature_github.presentation.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.clientapplication.R
import com.github.clientapplication.feature_github.presentation.MainActivity
import com.github.clientapplication.githubrepos.utils.Constant
import com.github.clientapplication.githubrepos.utils.Constant.KEY_ERROR
import com.github.clientapplication.githubrepos.utils.Constant.PARAMETER_CLIENT_ID
import com.github.clientapplication.githubrepos.utils.Constant.PARAMETER_SCOPE
import com.github.clientapplication.githubrepos.utils.Constant.TAG
import com.github.clientapplication.githubrepos.utils.Constant.VALUE_SCOPE
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModelAuth: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        if (viewModelAuth.getToken().isNullOrBlank()) {
            Log.d(TAG, "viewModelAuth.getToken().isNullOrBlank() webAuth")
            webAuth(this)
        } else {
            Log.d(TAG, "viewModelAuth.getToken().isNullOrBlank() openMainActivity")
            openMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        try {
            val uri: Uri? = intent?.data
            if (uri != null) {
                val code = uri.getQueryParameter(Constant.KEY_CODE)
                if (code != null) {
                    Log.d(TAG, "code: $code")
                    viewModelAuth.getAccessToken(code = code)
                    viewModelAuth.accessToken.observe(this) { accessToken ->
                        Log.d(TAG, "TOKEN: $accessToken")
                        if (accessToken.toString().isNotEmpty()) {
                           openMainActivity()
                        }
                    }
                } else if ((uri.getQueryParameter(KEY_ERROR)) != null) {
                    Log.d(TAG, "error: ${uri.getQueryParameter(KEY_ERROR)}")
                    Toast.makeText(
                        this,
                        this.resources.getText(R.string.msg_auth_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        catch (e: Exception){
            Log.d(TAG, e.localizedMessage)
        }
    }

    private fun webAuth(context: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "${Constant.OAUTH_LOGIN_URL}?${PARAMETER_CLIENT_ID}=${Constant.CLIENT_ID}&${PARAMETER_SCOPE}=${VALUE_SCOPE}"
            )
        )
        context.startActivity(intent)
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}