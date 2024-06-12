package interactor

import datasource.CostOfLivingDataSource

class FindCityForEmployeeDinnerInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(): String {
        // Fetch meal price data for cities in North America
        val allCities = dataSource.getAllCitiesData()
            .filter { it.countryName in listOf("USA", "Canada", "Mexico") }

        // Filter out low-quality data and cities with missing or negative meal prices
        val validCities = allCities.filter { city ->
            city.dataQuality &&
                    city.mealPrices.averagePrice != null &&
                    city.mealPrices.averagePrice!! > 0
        }

        // Sort cities by meal prices in ascending and descending order
        val sortedByAscending = validCities.sortedBy { it.mealPrices.averagePrice!! }
        val sortedByDescending = validCities.sortedByDescending { it.mealPrices.averagePrice!! }

        // Find the city with meal prices exactly between the most expensive and cheapest cities
        val mostExpensiveCity = sortedByDescending.first().mealPrices.averagePrice!!
        val cheapestCity = sortedByAscending.first().mealPrices.averagePrice!!

        val targetPrice = (mostExpensiveCity + cheapestCity) / 2

        val targetCity = validCities.find { it.mealPrices.averagePrice == targetPrice }

        return targetCity?.cityName ?: "No suitable city found"
    }
}
