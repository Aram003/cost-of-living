package interactor

import datasource.CostOfLivingDataSource

class GetTopCountriesWithHighCarbonatedDrinkTaxesInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(): List<Pair<String, Double>> {
        // Fetch all country data from the data source
        val allCountries = dataSource.getAllCountriesData()

        // Filter out low-quality data and countries with missing or negative carbonated drink prices
        val validCountries = allCountries.filter { country ->
            country.dataQuality &&
                    country.carbonatedDrinkPrices.averagePrice != null &&
                    country.carbonatedDrinkPrices.averagePrice!! > 0
        }

        // Sort countries by the average prices of carbonated drinks and take the top 10
        val sortedCountries = validCountries.sortedByDescending { country ->
            country.carbonatedDrinkPrices.averagePrice!!
        }.take(10)

        // Return a list of pairs containing country names and the average prices of carbonated drinks
        return sortedCountries.map { country ->
            country.countryName to country.carbonatedDrinkPrices.averagePrice!!
        }
    }
}
