package com.example.mycity.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.Modifier
import com.example.mycity.data.Place

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.mycity.R
import com.example.mycity.data.CategoryType


@Composable
fun MyCityHomeScreen(
    myCityUiState: MyCityUiState,
    onCategoryPressed: (CategoryType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            categoryType = CategoryType.REST,
            icon = Icons.Default.Place,
            text = stringResource(id = R.string.rest_category)
        ),
        NavigationItemContent(
            categoryType = CategoryType.NATURE,
            icon = Icons.Default.Face,
            text = stringResource(id = R.string.nature_category)
        ),
        NavigationItemContent(
            categoryType = CategoryType.SHOPPING,
            icon = Icons.Default.ShoppingCart,
            text = stringResource(id = R.string.shopping_category)
        )
    )

    if (myCityUiState.isShowingHomepage) {
        MyCityAppContent(
            myCityUiState = myCityUiState,
            onCategoryPressed = onCategoryPressed,
            onPlaceCardPressed = onPlaceCardPressed,
            navigationItemContentList = navigationItemContentList,
            modifier = modifier,
        )
    } else {
         MyCityDetailsScreen(
             myCityUiState = myCityUiState,
             onBackPressed = onDetailScreenBackPressed,
             modifier = modifier,
             isFullScreen = true
         )
    }
}



@Composable
fun MyCityAppContent(
    myCityUiState: MyCityUiState,
    onCategoryPressed: (CategoryType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                MyCityListOnlyContent(
                    myCityUiState = myCityUiState,
                    onPlaceCardPressed = onPlaceCardPressed,
                    modifier = Modifier
                        .weight(1f)
                        .padding()
                )
                ReplyBottomNavigationBar(
                    currentCategory = myCityUiState.currentCategory,
                    onCategoryPressed = onCategoryPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun ReplyBottomNavigationBar(
    currentCategory: CategoryType,
    onCategoryPressed: (CategoryType) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentCategory == navItem.categoryType,
                onClick = { onCategoryPressed(navItem.categoryType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}


data class NavigationItemContent(
    val categoryType: CategoryType,
    val icon: ImageVector,
    val text: String
)