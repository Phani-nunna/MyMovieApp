package com.practise.mymovieapp.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.practise.mymovieapp.R
import com.practise.mymovieapp.moviesList.data.remote.MovieApi
import com.practise.mymovieapp.moviesList.util.RatingBar

@Composable
fun DetailsScreen() {
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsStateWithLifecycle().value
    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.movie?.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state
    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.movie?.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (backDropImageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.dp_220))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dp_70)),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = detailsState.movie?.title
                )
            }
        }
        if (backDropImageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp),
                painter = backDropImageState.painter,
                contentDescription = detailsState.movie?.title,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dp_16))
        ) {
            Box(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dp_160))
                    .height(dimensionResource(id = R.dimen.dp_240))
            ) {
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_12)))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dp_70)),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailsState.movie?.title
                        )
                    }
                }
                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.dp_12))),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.movie?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            detailsState.movie?.let { movie ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_10)),
                        text = movie.title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
                    Row(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.dp_16))
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
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_12)))
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_16)),
                        text = stringResource(R.string.language) + movie.original_language
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_10)))
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_16)),
                        text = stringResource(R.string.release_date) + movie.release_date
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_10)))
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_10)),

                        text = movie.vote_count.toString() + stringResource(R.string.votes)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))

        Text(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_16)),
            text = stringResource(R.string.overview),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_8)))
        detailsState.movie?.let {
            Text(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dp_16)),
                text = it.overview,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_32)))
    }
}