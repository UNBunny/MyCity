package com.example.mycity.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.data.CategoryType
import com.example.mycity.data.Place

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier,
) {
    val viewModel: MyCityViewModel = viewModel()
    val myCityUiState = viewModel.uiState.collectAsState().value

    MyCityHomeScreen(
        myCityUiState = myCityUiState,
        onCategoryPressed = { categoryType: CategoryType ->
            viewModel.updateCurrentCategory(categoryType = categoryType)
            viewModel.resetHomeScreenState()
        },
        onPlaceCardPressed = { place: Place ->
            viewModel.updateDetailsScreenState(
                place = place
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenState()
        },
        modifier = modifier
    )
}
