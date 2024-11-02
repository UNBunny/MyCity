package com.example.mycity.data

import com.example.mycity.R

object LocalPlacesDataProvider {

    val defaultPlace = Place(
        1L,
        R.string.park_name,
        R.string.park_description,
        R.drawable.ic_launcher_background,
        CategoryType.NATURE
    )
    val allPlaces = listOf(
        Place(
            1L,
            R.string.park_name,
            R.string.park_description,
            R.drawable.ic_launcher_background,
            CategoryType.NATURE
        ),

        Place(
            8L,
            R.string.park_name,
            R.string.park_description,
            R.drawable.ic_launcher_background,
            CategoryType.NATURE
        ),
        Place(
            2L,
            R.string.museum_name,
            R.string.museum_description,
            R.drawable.ic_launcher_background,
            CategoryType.REST
        ),
        Place(
            3L,
            R.string.shopping_category,
            R.string.park_description,
            R.drawable.ic_launcher_background,
            CategoryType.SHOPPING
        )
        // Add more places as needed
    )

    fun getPlacesByType(type: CategoryType): List<Place> {
        return allPlaces.filter { it.type == type }
    }
}