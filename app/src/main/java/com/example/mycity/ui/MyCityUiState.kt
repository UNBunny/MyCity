package com.example.mycity.ui

import com.example.mycity.data.CategoryType
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.data.Place

data class MyCityUiState(
    val categorizedPlaces: Map<CategoryType, List<Place>> = emptyMap(),
    val currentCategory: CategoryType = CategoryType.REST,
    val currentSelectedPlace: Place = LocalPlacesDataProvider.defaultPlace,
    val isShowingHomepage: Boolean = true
) {
    val currentCategoryPlaces: List<Place> by lazy { categorizedPlaces[currentCategory] ?: emptyList() }
}