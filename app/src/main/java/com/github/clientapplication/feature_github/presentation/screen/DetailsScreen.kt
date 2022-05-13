package com.github.clientapplication.feature_github.presentation.screen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.clientapplication.R
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.presentation.Effect
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.feature_github.presentation.ReposState
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun DetailsScreen(
    viewModel: MainViewModel,
    state: State<ReposState>,
    effectFlow: Flow<Effect>?) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Repository is loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color(0xFFD1FFF9),
            topBar = {
                AppBar()
            },
        ) {
            Box {
                state.value.repo.let {
                    RepoItem(viewModel = viewModel, state = state)
                    if (state.value.isLoading)
                        LoadingBar()
                }
            }
        }

}

@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_github),
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text("") },
        backgroundColor = MaterialTheme.colors.secondary
    )
}

@Composable
fun RepoItem(
    viewModel: MainViewModel,
    state: State<ReposState>
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ){
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
            }
                RepoDetails(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
        }
    }
}



@Composable
fun RepoDetails(
    viewModel: MainViewModel,
    modifier: Modifier
) {

    viewModel.state.value.repo.value?.let {
        Log.d(TAG, "TGTGTG RepoDetails "+ it.stars)
        Column(modifier = modifier) {
            Text(
                text = it.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 10.dp
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary,
            overflow = TextOverflow.Ellipsis
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp
                        ),
                    text = it.description.trim(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle2
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    var text = "${stringResource(R.string.label_language)} ${it.language.trim()}"
                    Text(
                        text = text,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption
                    )

                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    var text = "${stringResource(R.string.label_starred)} ${it.stars}"
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp
                            ),
                        text = text,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption
                    )
                }
                ButtonStar(
                    viewModel = viewModel,
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ButtonStar(viewModel: MainViewModel, modifier: Modifier){
        ExtendedFloatingActionButton(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    tint = Color.White,
                    contentDescription = null
                )
            },
            text = { Text(stringResource(R.string.label_add_star), color = Color.White) },
            backgroundColor = MaterialTheme.colors.secondary,
            onClick = {
                viewModel.addStar()
            },
            modifier = modifier
        )
}








