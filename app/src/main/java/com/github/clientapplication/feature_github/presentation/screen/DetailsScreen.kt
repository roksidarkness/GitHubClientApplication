package com.github.clientapplication.feature_github.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.github.clientapplication.feature_github.presentation.MainViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailsScreen(
    repoId: String,
    viewModel: MainViewModel
) {
    getDataRepo(repoId, viewModel)
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFD1FFF9),
        topBar = {
            AppBar()
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        if (errorMessage ?.isNotEmpty() == true) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = errorMessage.toString()
                )
                viewModel.clearErrorMessage()
            }
        }

        Box {
            val isLoadingRepo by viewModel.isLoadingRepo.observeAsState(initial = true)
            val repo by viewModel.repo.observeAsState()

            repo.let {
                RepoItem(repo = repo, viewModel::addStar)
                if (isLoadingRepo)
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
    repo: RepoEntity?,
    addStar: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
            }
            RepoDetails(
                repo = repo,
                addStar =addStar,
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
    repo: RepoEntity?,
    addStar: () -> Unit,
    modifier: Modifier
) {
    repo?.let {
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
                    addStar
                )
            }
        }
    }
}

@Composable
fun ButtonStar(addStar: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = {
            Icon(
                imageVector = Icons.Filled.Star,
                tint = Color.White,
                contentDescription = null
            )
        },
        text = { Text(stringResource(R.string.label_add_star), color = Color.White) },
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = {
            addStar()
        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

fun getDataRepo(repoId: String, viewModel: MainViewModel){
    viewModel.getLocalRepo(repoId)
    viewModel.clearErrorMessage()
}







