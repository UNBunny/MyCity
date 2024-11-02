package com.example.mycity.data

import com.example.mycity.R

object LocalPlacesDataProvider {

    val defaultPlace = Place(
        1L,
        R.string.soviet_park,
        R.string.soviet_park_description,
        R.drawable.soviet_park,
        CategoryType.NATURE
    )

    val allPlaces = listOf(
        defaultPlace,
        Place(
            2L,
            R.string.vokrug_sveta_park,
            R.string.vokrug_sveta_park_description,
            R.drawable.vokrug_sveta_park,
            CategoryType.NATURE
        ),
        Place(
            3L,
            R.string.let30_vlksm_park,
            R.string.let30_vlksm_park_description,
            R.drawable.let30_vlksm_park,
            CategoryType.NATURE
        ),

        Place(
            4L,
            R.string.roses_restraunt,
            R.string.roses_restraunt_description,
            R.drawable.roses_restraunt,
            CategoryType.REST
        ),
        Place(
            5L,
            R.string.coffee_anytime,
            R.string.coffee_anytime_description,
            R.drawable.anytime_coffee,
            CategoryType.REST
        ),
        Place(
            6L,
            R.string.shato_restraunt,
            R.string.shato_restraunt_description,
            R.drawable.shato_restraunt,
            CategoryType.REST
        ),
        Place(
            7L,
            R.string.skuratov_coffee,
            R.string.skuratov_coffee_description,
            R.drawable.skuratov_coffee,
            CategoryType.REST
        ),
        Place(
            8L,
            R.string.mega_shopping,
            R.string.mega_shopping_description,
            R.drawable.mega_shopping,
            CategoryType.SHOPPING
        ),
        Place(
            9L,
            R.string.new_dom_shopping,
            R.string.new_dom_shopping_description,
            R.drawable.noviy_dom_shopping,
            CategoryType.SHOPPING
        ),
        Place(
            10L,
            R.string.triumph_shopping,
            R.string.triumph_shopping_description,
            R.drawable.triumph_shopping,
            CategoryType.SHOPPING
        ),
        // Add more places as needed
    )

    fun getPlacesByType(type: CategoryType): List<Place> {
        return allPlaces.filter { it.type == type }
    }
}