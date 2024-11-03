package com.example.mycity.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.mycity.R
import com.example.mycity.data.CategoryType
import com.example.mycity.data.Place
import com.example.mycity.ui.utils.MyCityContentType
import com.example.mycity.ui.utils.MyCityNavigationType

@Composable
fun MyCityHomeScreen(
    myCityUiState: MyCityUiState,
    onCategoryPressed: (CategoryType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    navigationType: MyCityNavigationType,
    contentType: MyCityContentType
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
    if (navigationType == MyCityNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    Modifier.width(dimensionResource(R.dimen.drawer_width)),
                    drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                ) {
                    NavigationDrawerContent(
                        selectedDestination = myCityUiState.currentCategory,
                        onTabPressed = onCategoryPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(dimensionResource(R.dimen.drawer_padding_content))
                    )
                }
            },
            modifier = Modifier.testTag(navigationDrawerContentDescription)
        ) {
            MyCityAppContent(
                navigationType = navigationType,
                contentType = contentType,
                myCityUiState = myCityUiState,
                onCategoryPressed = onCategoryPressed,
                onPlaceCardPressed = onPlaceCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier,
            )
        }
    } else {
        if (myCityUiState.isShowingHomepage) {
            MyCityAppContent(
                navigationType = navigationType,
                contentType = contentType,
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
}


@Composable
fun MyCityAppContent(
    navigationType: MyCityNavigationType,
    contentType: MyCityContentType,
    myCityUiState: MyCityUiState,
    onCategoryPressed: (CategoryType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = navigationType == MyCityNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                MyCityNavigationRail(
                    currentTab = myCityUiState.currentCategory,
                    onCategoryPressed = onCategoryPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(navigationRailContentDescription)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                if (contentType == MyCityContentType.LIST_AND_DETAIL) {
                    MyCityListAndDetailContent(
                        myCityUiState = myCityUiState,
                        onPlaceCardPressed = onPlaceCardPressed,
                        modifier = Modifier
                            .statusBarsPadding()
                            .weight(1f)
                    )
                } else {
                    MyCityListOnlyContent(
                        myCityUiState = myCityUiState,
                        onPlaceCardPressed = onPlaceCardPressed,
                        modifier = Modifier
                            .weight(1f)
                            .padding()
                    )
                }
                AnimatedVisibility(
                    visible = navigationType == MyCityNavigationType.BOTTOM_NAVIGATION
                ) {
                    val bottomNavigationContentDescription =
                        stringResource(R.string.navigation_bottom)
                    ReplyBottomNavigationBar(
                        currentCategory = myCityUiState.currentCategory,
                        onCategoryPressed = onCategoryPressed,
                        navigationItemContentList = navigationItemContentList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(bottomNavigationContentDescription)
                    )
                }

            }
        }
    }
}

@Composable
private fun MyCityNavigationRail(
    currentTab: CategoryType,
    onCategoryPressed: ((CategoryType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.categoryType,
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


@Composable
private fun NavigationDrawerContent(
    selectedDestination: CategoryType,
    onTabPressed: ((CategoryType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.profile_image_padding)),
        )
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.categoryType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.categoryType) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
    }
}

data class NavigationItemContent(
    val categoryType: CategoryType,
    val icon: ImageVector,
    val text: String
)