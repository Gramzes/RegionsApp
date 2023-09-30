package com.gramzin.regionsapp.presentation.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gramzin.regionsapp.R
import com.gramzin.regionsapp.domain.model.Region

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onRegionClick: (region: Region) -> Unit
){
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Список регионов")
                },
                actions = {
                    IconButton(
                        onClick = viewModel::refreshRegions
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            when (val currentState = state.value){
                is MainScreenState.Data -> {
                    RegionsContent(
                        state = currentState,
                        onRefresh = viewModel::refreshRegions,
                        onRegionClick = onRegionClick,
                        onChangeLikeState = {
                            viewModel.changeLikeState(region = it)
                        }
                    )
                }
                MainScreenState.Error -> { }
                MainScreenState.Initial -> { }
                MainScreenState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegionsContent(
    state: MainScreenState.Data,
    onRefresh: () -> Unit,
    onRegionClick: (Region) -> Unit,
    onChangeLikeState: (Region) -> Unit
){
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = onRefresh
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            items(
                items = state.regions,
                key = { it.id }
            ) {
                RegionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRegionClick(it)
                        },
                    region = it,
                    onLikeClick = {
                        onChangeLikeState(it)
                    }
                )
            }
        }

        PullRefreshIndicator(
            refreshing = state.isRefresh,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun RegionItem(
    region: Region,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = region.imageUrls.first(),
                contentDescription = region.name,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = region.name)
        }
        IconButton(
            onClick = onLikeClick
        ) {
            val likeRes = if (region.isFavorite) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = ImageVector.vectorResource(id = likeRes),
                contentDescription = "",
                tint = if (region.isFavorite) Color.Red else MaterialTheme.colors.onBackground
            )
        }

    }
}