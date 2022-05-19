package com.github.clientapplication.feature_github.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.github.clientapplication.feature_github.presentation.navigation.Navigation
import com.github.clientapplication.githubrepos.utils.pxToDp
import com.github.clientapplication.ui.theme.GitHubClientApplicationTheme
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubClientApplicationTheme {
                Surface(color = Color(0xFFD1FFF9), modifier = Modifier.fillMaxSize()) {
                    Navigation(viewModel, calculateScreenHeight(), calculateScreenWidth())
                }
            }
        }
    }

    private fun calculateScreenWidth(): Int{
        val mWidth = this.resources.displayMetrics.widthPixels
        return pxToDp(mWidth)
    }

    private fun calculateScreenHeight(): Int{
        val mHeight = this.resources.displayMetrics.heightPixels
        return pxToDp(mHeight)
    }
}

