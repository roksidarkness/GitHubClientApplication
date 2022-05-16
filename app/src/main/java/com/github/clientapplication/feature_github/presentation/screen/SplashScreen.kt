package com.github.clientapplication.feature_github.presentation.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
fun SplashScreen(navController: NavController, height: Int, weight: Int) {
    val scale = remember {
        Animatable(INIT)
    }

    val sizeIcon = 200
    val sizeIconEnd = 60
    val h = height/2.toFloat()
    val w = weight/2.toFloat() - sizeIcon/4

    var isAnimated by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isAnimated, label = "transition")

    val iconOffset by transition.animateOffset(transitionSpec = {
        if (this.targetState) {
            tween(1300)

        } else {
            tween(1300)
        }
    }, label = "GitHub icon animated") { animated ->
        if (animated) Offset(0f, 0f) else Offset(w, h)
    }

    val iconSize by transition.animateDp(transitionSpec = {
        tween(1300)
    }, "") { animated ->
        if (animated) sizeIconEnd.dp else sizeIcon.dp
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
        isAnimated = !isAnimated
        delay(1300L)
        navController.popBackStack()
        navController.navigate(NavRoutes.Main.route)
    }

        Image(painter = painterResource(id = R.drawable.ic_github),
           contentDescription = "Github Icon",
            modifier = Modifier
                .scale(scale.value)
                .size(iconSize)
                .alpha(1.0f)
                .offset(iconOffset.x.dp, iconOffset.y.dp))
}

