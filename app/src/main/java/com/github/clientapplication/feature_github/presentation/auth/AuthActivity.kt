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
import com.github.clientapplication.githubrepos.utils.Constants
import com.github.clientapplication.githubrepos.utils.Constants.KEY_ERROR
import com.github.clientapplication.githubrepos.utils.Constants.PARAMETER_CLIENT_ID
import com.github.clientapplication.githubrepos.utils.Constants.PARAMETER_SCOPE
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import com.github.clientapplication.githubrepos.utils.Constants.VALUE_SCOPE
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

        if (viewModelAuth.getToken().isNullOrBlank()) {
            webAuth(this)
        } else {
            openMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent?.data
        if (uri != null) {
            val code = uri.getQueryParameter(Constants.KEY_CODE)
            if (code != null) {
                Log.d(TAG, "code: $code")
                viewModelAuth.getAccessToken(code = code)
                viewModelAuth.accessToken.observe(this) { accessToken ->
                    Log.d(TAG, "TOKEN: $accessToken")
                    if (accessToken.toString().isNotBlank()) {
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

    private fun webAuth(context: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "${Constants.OAUTH_LOGIN_URL}?${PARAMETER_CLIENT_ID}=${Constants.CLIENT_ID}&${PARAMETER_SCOPE}=${VALUE_SCOPE}"
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