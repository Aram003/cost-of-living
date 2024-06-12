package interactor

import datasource.CostOfLivingDataSource

class SortCitiesByCheapestBananaPriceInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(vararg cities: String): List<String> {
        // Create a mutable map to store city names and their corresponding banana prices
        val cityBananaPrices = mutableMapOf<String, Double>()

        // Fetch banana prices for each city
        cities.forEach { city ->
            val bananaPrice = dataSource.getBananaPrice(city)
            if (bananaPrice != null) {
                cityBananaPrices[city] = bananaPrice
            }
        }

        // Sort cities by their cheapest banana prices
        val sortedCities = cityBananaPrices.toList().sortedBy { (_, price) -> price }

        // Return a sorted list of city names
        return sortedCities.map { (city, _) -> city }
    }
}
