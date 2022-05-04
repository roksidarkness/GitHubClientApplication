package com.github.clientapplication.feature_github.presentation.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.github.clientapplication.feature_github.presentation.MainActivity
import com.github.clientapplication.githubrepos.utils.Constants
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import com.github.clientapplication.ui.theme.GitHubClientApplicationTheme
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
        }
        else{
            openMainActivity()
        }


        setContent {
            GitHubClientApplicationTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Loading...", color = Color.Black, fontSize = 24.sp)
                }
            }
        }
    }

    override fun onResume() {
        Log.d(Constants.TAG, "onResume")
        super.onResume()
        val uri: Uri? = intent?.data
        Log.d(TAG, "uri "+uri.toString())
        if (uri != null){
            val code = uri.getQueryParameter(Constants.KEY_CODE)
            if(code != null){
                Log.d(TAG, "code: $code")
                Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()

                viewModelAuth.getAccessToken(code = code)
                Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()
                viewModelAuth.accessToken.observe(this) { accessToken ->
                    Log.d(TAG, "TOKEN: $accessToken")
                    if (accessToken.toString().isNotBlank()) {
                        openMainActivity()
                    }
                }
            } else if((uri.getQueryParameter("error")) != null){
                Log.d(TAG, "error: ${uri.getQueryParameter("error")}")
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun webAuth(context: Context){
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "${Constants.OAUTH_LOGIN_URL}?client_id=${Constants.CLIENT_ID}&scope=repo"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    private fun openMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}