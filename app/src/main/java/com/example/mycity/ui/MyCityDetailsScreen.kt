package com.example.mycity.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.CategoryType
import com.example.mycity.data.Place
import kotlin.random.Random

@Composable
fun MyCityDetailsScreen(
    myCityUiState: MyCityUiState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    BackHandler {
        onBackPressed()
    }
    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
            ),
            modifier = Modifier
                .testTag(stringResource(R.string.details_screen))
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            item {
                if (isFullScreen) {
                    ReplyDetailsScreenTopBar(
                        onBackPressed,
                        myCityUiState,
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = dimensionResource(R.dimen.detail_topbar_padding_bottom),
                                top = dimensionResource(R.dimen.topbar_padding_vertical)
                            )
                    )
                }
                ReplyEmailDetailsCard(
                    place = myCityUiState.currentSelectedPlace,
                    categoryType = myCityUiState.currentCategory,
                    isFullScreen = isFullScreen,
                    modifier = if (isFullScreen) {
                        Modifier.padding(horizontal = dimensionResource(R.dimen.detail_card_outer_padding_horizontal))
                    } else {
                        Modifier
                    }
                )
            }
        }
    }
}

@Composable
private fun ReplyDetailsScreenTopBar(
    onBackButtonClicked: () -> Unit,
    myCityUiState: MyCityUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal))
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.detail_subject_padding_end))
        ) {
            Text(
                text = stringResource(myCityUiState.currentSelectedPlace.name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
private fun ReplyEmailDetailsCard(
    place: Place,
    categoryType: CategoryType,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    val context = LocalContext.current
    val displayToast = { text: String ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.detail_card_inner_padding))
        ) {
            // Image at the top
            Image(
                painter = painterResource(place.img), // Assuming place has an imageUrl property
                contentDescription = stringResource(place.description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
            )

            // Description below the image
            Text(
                text = stringResource(place.description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.detail_content_padding_top)
                )
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.detail_content_padding_top)))

            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("google.navigation:q=${generateRandomCoordinates()}") // Use place's coordinates
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    context.startActivity(mapIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.topbar_profile_image_size)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(R.string.build_route))
            }
        }
    }
}

//
//@Composable
//private fun DetailsScreenButtonBar(
//    categoryType: CategoryType,
//    displayToast: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Box(modifier = modifier) {
//        when (categoryType) {
//            CategoryType.REST ->
//                ActionButton(
//                    text = stringResource(id = R.string.continue_composing),
//                    onButtonClicked = displayToast
//                )
//
//            CategoryType.NATURE ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)
//                        ),
//                    horizontalArrangement = Arrangement.spacedBy(
//                        dimensionResource(R.dimen.detail_button_bar_item_spacing)
//                    ),
//                ) {
//                    ActionButton(
//                        text = stringResource(id = R.string.move_to_inbox),
//                        onButtonClicked = displayToast,
//                        modifier = Modifier.weight(1f)
//                    )
//                    ActionButton(
//                        text = stringResource(id = R.string.delete),
//                        onButtonClicked = displayToast,
//                        containIrreversibleAction = true,
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//
//            MailboxType.Sent, MailboxType.Inbox ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)
//                        ),
//                    horizontalArrangement = Arrangement.spacedBy(
//                        dimensionResource(R.dimen.detail_button_bar_item_spacing)
//                    ),
//                ) {
//                    ActionButton(
//                        text = stringResource(id = R.string.reply),
//                        onButtonClicked = displayToast,
//                        modifier = Modifier.weight(1f)
//                    )
//                    ActionButton(
//                        text = stringResource(id = R.string.reply_all),
//                        onButtonClicked = displayToast,
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//        }
//    }
//}

fun generateRandomCoordinates(): Pair<Double, Double> {
    val latitude = Random.nextDouble(-90.0, 90.0) // Random latitude between -90 and 90
    val longitude = Random.nextDouble(-180.0, 180.0) // Random longitude between -180 and 180
    return Pair(latitude, longitude)
}