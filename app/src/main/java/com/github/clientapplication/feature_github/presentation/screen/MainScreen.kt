package com.github.clientapplication.feature_github.presentation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.clientapplication.R
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xFFD1FFF9),
        topBar = {
            MainAppBar()
        },
    ) {
        Box {
            val isLoading by viewModel.isLoading.observeAsState(initial = true)
            val repos by viewModel.repos.observeAsState(initial = emptyList())

            RepoList(repoItems = repos) { id ->
                navController.navigate(NavRoutes.RepoDetails.passRepoId(id))
            }
            if (isLoading) {
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
fun RepoList(
    repoItems: List<RepoEntity>,
    onItemClicked: (id: String) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(repoItems) { item ->
            RepoItemRow(item = item, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun RepoItemRow(
    item: RepoEntity,
    onItemClicked: (id: String) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onItemClicked(item.id) }
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            Box() {
            }
            RepoItemDetails(
                item = item,
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
fun RepoItemDetails(
    item: RepoEntity,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 10.dp
                ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (item.description.trim().isNotEmpty())
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp
                        ),
                    text = item.description.trim(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        if (item.language.trim().isNotEmpty()) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                val text = "${stringResource(R.string.label_language)} ${item.language.trim()}"
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption
                )
            }
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val text = "${stringResource(R.string.label_starred)} ${item.stars}"
            Text(
                text = text,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun LoadingBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

