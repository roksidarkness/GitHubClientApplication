package com.github.clientapplication.feature_github.presentation.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.github.clientapplication.R
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes
import com.github.clientapplication.feature_github.presentation.screen.Anim.DELAY
import com.github.clientapplication.feature_github.presentation.screen.Anim.DURATION_MILLIS
import com.github.clientapplication.feature_github.presentation.screen.Anim.INIT
import com.github.clientapplication.feature_github.presentation.screen.Anim.TARGET_VALUE
import com.github.clientapplication.feature_github.presentation.screen.Anim.TENSION
import kotlinx.coroutines.delay

object Anim {
    const val DELAY = 3000L
    const val DURATION_MILLIS = 300
    const val TARGET_VALUE = 0.7f
    const val TENSION = 4f
    const val INIT = 0f
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(INIT)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = TARGET_VALUE,
            animationSpec = tween(
                durationMillis = DURATION_MILLIS,
                easing = {
                    OvershootInterpolator(TENSION).getInterpolation(it)
                })
        )
        delay(DELAY)
        navController.popBackStack()
        navController.navigate(NavRoutes.Main.route)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_github),
            contentDescription = "Github Icon",
            modifier = Modifier.scale(scale.value))
    }
}