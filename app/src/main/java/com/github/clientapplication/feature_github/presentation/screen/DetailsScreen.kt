package com.github.clientapplication.feature_github.presentation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.clientapplication.R
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.presentation.Effect
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.feature_github.presentation.ReposState
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes
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
        topBar = {
            MainAppBar()
        },
    ) {
        Box {
            val item = state.value.repo
            item?.let {
                RepoItem(viewModel = viewModel, item = it)
                if (state.value.isLoading)
                    LoadingBar()
                }
        }
    }
}

@Composable
private fun MainAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun RepoItem(
    viewModel: MainViewModel,
    item: RepoEntity
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ){
        var expanded by rememberSaveable { mutableStateOf(false) }
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
            }
                RepoDetails(
                    viewModel = viewModel,
                    item = item,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 24.dp,
                            bottom = 24.dp
                        )
                        .fillMaxWidth(0.80f)
                        .align(Alignment.CenterVertically)
                )
        }
    }
}



@Composable
fun RepoDetails(
    viewModel: MainViewModel,
    item: RepoEntity,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.description.trim(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    var text = "${stringResource(R.string.label_language)} ${item.language.trim()}"
                    Text(
                        text = text,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption
                    )

                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    var text = "${stringResource(R.string.label_starred)} ${item?.stars}"
                    Text(
                        text = text,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.caption
                    )
                }
                buttonStar(viewModel = viewModel)
            }
    }
}

@Composable
fun buttonStar(viewModel: MainViewModel){
    Button(
        onClick = {
            viewModel.addStar()
        }) {
        Text(stringResource(R.string.label_add_star))
    }
}



