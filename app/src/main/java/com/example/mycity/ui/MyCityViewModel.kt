package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.CategoryType
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val categorizedPlaces: Map<CategoryType, List<Place>> =
            LocalPlacesDataProvider.allPlaces.groupBy { it.type }
        _uiState.value = MyCityUiState(
            categorizedPlaces = categorizedPlaces,
            currentSelectedPlace = categorizedPlaces[CategoryType.REST]?.firstOrNull()
                ?: LocalPlacesDataProvider.allPlaces.firstOrNull()
                ?: LocalPlacesDataProvider.defaultPlace
        )
    }
    fun updateDetailsScreenState(place: Place) {
        _uiState.update {
            it.copy(
                currentSelectedPlace = place,
                isShowingHomepage = false
            )
        }
    }
    fun resetHomeScreenState() {
        _uiState.update {
            it.copy(
                currentSelectedPlace = it.categorizedPlaces[it.currentCategory]?.firstOrNull()
                    ?: LocalPlacesDataProvider.defaultPlace,
                isShowingHomepage = true
            )
        }
    }
    fun updateCurrentCategory(categoryType: CategoryType) {
        _uiState.update {
            it.copy(
                currentCategory = categoryType
            )
        }
    }
}
