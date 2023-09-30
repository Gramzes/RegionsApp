package com.gramzin.regionsapp.presentation.screens.details_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.gramzin.regionsapp.R
import com.gramzin.regionsapp.domain.model.Region

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBackButton: () -> Unit
){
    var selectedImage by remember { mutableStateOf<String?>(null) }
    var region by remember { mutableStateOf<Region?>(null) }

    val state = viewModel.state.collectAsState()

    selectedImage?.let{
        BackHandler(onBack = { selectedImage = null })
        ImageDialog(imageUrl = it){
            selectedImage = null
        }
    }

    Scaffold(
        topBar = {
            DetailsScreenTopBar(
                region = region,
                onBackClick = onBackButton,
                onLikeClick = {
                    region?.let { viewModel.changeLikeState(it) }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (val currentState = state.value) {
                is DetailsScreenState.Data -> {
                    region = currentState.region
                    RegionContent(
                        region = currentState.region,
                        onImageClick = {
                            selectedImage = it
                        }
                    )
                }

                DetailsScreenState.Error -> {

                }
                DetailsScreenState.Initial -> {}
                DetailsScreenState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun RegionContent(
    region: Region,
    onImageClick: (String) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(
                items = region.imageUrls
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            onImageClick(it)
                        },
                    model = it,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
fun DetailsScreenTopBar(
    region: Region?,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit
){
    TopAppBar(
        title = {
            Column() {
                Text(
                    text = region?.name ?: "",
                    fontSize = 20.sp
                )
                Text(
                    text = (region?.viewsCount ?: 0).toString()
                            + stringResource(R.string.views_count),
                    fontSize = 16.sp
                )
            }
        },
        navigationIcon = {
            var isButtonEnabled by remember {
                mutableStateOf(true)
            }
            IconButton(
                onClick = {
                    onBackClick()
                    isButtonEnabled = false
                },
                enabled = isButtonEnabled
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            region?.let {
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
                        tint = if (region.isFavorite) Color.Red
                        else MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    )
}

@Composable
fun ImageDialog(
    imageUrl: String,
    onDismissDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image",
            contentScale = ContentScale.Fit
        )
    }
}
