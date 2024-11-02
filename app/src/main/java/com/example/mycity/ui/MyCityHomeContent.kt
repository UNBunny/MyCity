package com.example.mycity.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.mycity.R
import com.example.mycity.data.Place
import com.example.mycity.data.CategoryType

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
                onCardClick = { onPlaceCardPressed(place) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityPlaceListItem(
    place: Place,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
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
        MyCityProfileImage(
            drawableResource = place.img, // Assuming icon is an Int in your Place data class
            description = stringResource(place.name),
            modifier = Modifier.size(dimensionResource(R.dimen.place_header_profile_size))
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
fun MyCityProfileImage(
    @DrawableRes drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.clip(CircleShape),
            painter = painterResource(drawableResource),
            contentDescription = description,
        )
    }
}

@Composable
fun MyCityLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = stringResource(R.string.shopping_category),
        colorFilter = ColorFilter.tint(color),
        modifier = modifier
    )
}

@Composable
private fun MyCityHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MyCityLogo(
            modifier = Modifier
                .size(dimensionResource(R.dimen.topbar_logo_size))
                .padding(start = dimensionResource(R.dimen.topbar_logo_padding_start))
        )
        MyCityProfileImage(
            drawableResource = R.drawable.ic_launcher_background, // Replace with actual avatar resource
            description = stringResource(R.string.nature_category),
            modifier = Modifier
                .padding(end = dimensionResource(R.dimen.topbar_profile_image_padding_end))
                .size(dimensionResource(R.dimen.topbar_profile_image_size))
        )
    }
}
