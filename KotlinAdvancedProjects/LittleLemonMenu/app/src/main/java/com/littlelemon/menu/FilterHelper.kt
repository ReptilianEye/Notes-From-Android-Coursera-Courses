package com.littlelemon.menu

class FilterHelper {
    fun filterProducts(type: FilterType, productsList: List<ProductItem>): List<ProductItem> {
        return when (type) {
            FilterType.All -> productsList
            FilterType.Dessert -> productsList.filter { product -> product.category == "Dessert" }
            FilterType.Drinks -> productsList.filter { product -> product.category == "Drinks" }
            FilterType.Food -> productsList.filter { product -> product.category == "Food" }
        }
    }

}