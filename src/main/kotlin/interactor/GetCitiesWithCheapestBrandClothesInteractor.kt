package interactor

import datasource.CostOfLivingDataSource

class GetCitiesWithCheapestBrandClothesInteractor(
    private val dataSource: CostOfLivingDataSource,
) {

    fun execute(limit: Int = 5): List<String> {
        // Fetch all city data from the data source
        val allCities = dataSource.getAllCitiesData()

        // Filter out cities with low data quality or null clothes prices
        val validCities = allCities.filter { city ->
            city.clothesPrices.onePairOfJeansLevis50oneOrSimilar != null &&
                    city.dataQuality
        }

        // Sort cities by the price of clothes from famous brands (e.g., Levi's jeans)
        val sortedCities = validCities.sortedBy { city ->
            city.clothesPrices.onePairOfJeansLevis50oneOrSimilar!!
        }

        // Take the top 'limit' cities and return their names
        return sortedCities.take(limit).map { city ->
            city.cityName
        }
    }
}
