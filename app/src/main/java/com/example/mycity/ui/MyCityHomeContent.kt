package com.example.mycity.ui

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Place


@Composable
fun MyCityListAndDetailContent(
    myCityUiState: MyCityUiState,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val places = myCityUiState.currentCategoryPlaces
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        LazyColumn(
            contentPadding = WindowInsets.statusBars.asPaddingValues(),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.place_list_only_horizontal_padding)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.place_list_item_vertical_spacing)
            )
        ) {
            items(places, key = { place -> place.id }) { place ->
                MyCityPlaceListItem(
                    place = place,
                    selected = myCityUiState.currentSelectedPlace.id == place.id,
                    onCardClick = { onPlaceCardPressed(place) }
                )
            }

        }
        val activity = LocalContext.current as Activity
        MyCityDetailsScreen(
            myCityUiState = myCityUiState,
            onBackPressed = { activity.finish() },
            modifier = Modifier
                .weight(1f)
                .padding(end = dimensionResource(R.dimen.place_list_only_horizontal_padding)),
        )
    }
}

@Composable
fun MyCityListOnlyContent(
    myCityUiState: MyCityUiState,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val places = myCityUiState.currentCategoryPlaces

    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.place_list_item_vertical_spacing)
        )
    ) {
        item {
            MyCityHomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.topbar_padding_vertical))
            )
        }
        items(places, key = { place -> place.id }) { place ->
            MyCityPlaceListItem(
                place = place,
                selected = false,
                onCardClick = { onPlaceCardPressed(place) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityPlaceListItem(
    place: Place,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.place_list_only_horizontal_padding)), // Добавление отступа
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.place_list_item_inner_padding))
        ) {
            MyCityPlaceItemHeader(
                place,
                Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(place.name),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.place_list_item_header_subject_spacing),
                    bottom = dimensionResource(R.dimen.place_list_item_subject_body_spacing)
                ),
            )
            Text(
                text = stringResource(place.description),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MyCityPlaceItemHeader(place: Place, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        MyCityPlaceImage(
            drawableResource = place.img, // Assuming icon is an Int in your Place data class
            description = stringResource(place.name),
            modifier = Modifier
                .size(dimensionResource(R.dimen.place_header_profile_size))
                .clip(
                    CircleShape
                )
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.place_header_content_padding_horizontal),
                    vertical = dimensionResource(R.dimen.place_header_content_padding_vertical)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(place.name),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun MyCityPlaceImage(
    @DrawableRes drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(drawableResource),
            contentDescription = description,
            modifier = Modifier
                .size(dimensionResource(R.dimen.place_header_profile_size)) // Убедитесь, что размер одинаковый
                .clip(CircleShape) // Убедитесь, что изображение обрезано по кругу
                .fillMaxSize() // Это поможет изображению заполнить весь круг
        )
    }
}


@Composable
private fun MyCityHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.topbar_padding_vertical)) // Adjust padding as needed
    ) {
        Text(
            text = "OSMK МЕСТА",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
