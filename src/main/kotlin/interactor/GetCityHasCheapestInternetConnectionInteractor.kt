package interactor

import model.CityEntity
import datasource.CostOfLivingDataSource

class GetCityHasCheapestInternetConnectionInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(): CityEntity? {
        // Fetch data of all cities from the data source
        val allCities = dataSource.getAllCitiesData()

        // Filter out low-quality data and cities with missing or null internet prices for 60 Mbps connection
        val validCities = allCities.filter { city ->
            city.dataQuality &&
                    city.servicesPrices.internet60MbpsOrMoreUnlimitedDataCableAdsl != null
        }

        // Calculate affordability of internet connection based on city's average salary
        return validCities.minByOrNull { city ->
            val internetPrice = city.servicesPrices.internet60MbpsOrMoreUnlimitedDataCableAdsl!!
            val salary = city.salaryStatistics.averageSalary
            internetPrice / salary
        }
    }
}
