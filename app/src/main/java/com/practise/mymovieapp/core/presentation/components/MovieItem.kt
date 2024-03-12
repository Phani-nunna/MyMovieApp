package com.practise.mymovieapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.practise.mymovieapp.R
import com.practise.mymovieapp.moviesList.data.remote.MovieApi
import com.practise.mymovieapp.moviesList.domain.model.Movie
import com.practise.mymovieapp.moviesList.util.RatingBar
import com.practise.mymovieapp.moviesList.util.Screen
import com.practise.mymovieapp.moviesList.util.getAverageColor

@Composable
fun MovieItem(
    movie: Movie,
    navHostController: NavHostController
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + movie.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominateColor by remember {
        mutableStateOf(defaultColor)
    }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(dimensionResource(id = R.dimen.dp_200))
            .padding(dimensionResource(id = R.dimen.dp_8))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_28)))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominateColor
                    )
                )
            )
            .clickable {
                navHostController.navigate(Screen.Details.rout + "/${movie.id}")

            }

    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_6))
                    .height(dimensionResource(id = R.dimen.dp_250))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_22)))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dp_70)),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = movie.title
                )
            }
        }
        if (imageState is AsyncImagePainter.State.Success) {
            dominateColor = getAverageColor(
                imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap()
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_6))
                    .height(dimensionResource(id = R.dimen.dp_250))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_22))),
                painter = imageState.painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_6)))
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.dp_16),
                    end = dimensionResource(id = R.dimen.dp_8)
                ),
            text = movie.title,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.dp_16),
                    bottom = dimensionResource(id = R.dimen.dp_12),
                    top = dimensionResource(id = R.dimen.dp_4)
                )
        ) {
            RatingBar(
                starsModifier = Modifier.size(dimensionResource(id = R.dimen.dp_18)),
                rating = movie.vote_average / 2
            )
            Text(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.dp_4)),
                text = movie.vote_average.toString().take(3),
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1,
            )

        }

    }
}