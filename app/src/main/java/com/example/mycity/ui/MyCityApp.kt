package com.example.mycity.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.data.CategoryType
import com.example.mycity.data.Place
import com.example.mycity.ui.utils.MyCityContentType
import com.example.mycity.ui.utils.MyCityNavigationType

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass
) {
    val viewModel: MyCityViewModel = viewModel()
    val myCityUiState = viewModel.uiState.collectAsState().value

    val navigationType: MyCityNavigationType
    val contentType: MyCityContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = MyCityNavigationType.BOTTOM_NAVIGATION
            contentType = MyCityContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = MyCityNavigationType.NAVIGATION_RAIL
            contentType = MyCityContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = MyCityNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = MyCityContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = MyCityNavigationType.BOTTOM_NAVIGATION
            contentType = MyCityContentType.LIST_ONLY
        }
    }

    MyCityHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
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
