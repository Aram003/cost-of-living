package interactor

import model.CityEntity
import datasource.CostOfLivingDataSource

class GetCityWithHighestRentDifferenceInteractor(
    private val dataSource: CostOfLivingDataSource,
) {

    fun execute(): CityEntity {
        // Fetch all city data from the data source
        val allCities = dataSource.getAllCitiesData()

        // Filter out cities with low data quality or null rent values
        val validCities = allCities.filter { city ->
            city.realEstatesPrices.apartment3BedroomsInCityCentre != null &&
                    city.realEstatesPrices.apartment3BedroomsOutsideOfCentre != null &&
                    city.dataQuality
        }

        // Calculate the city with the highest rent difference
        val cityWithHighestRentDifference = validCities.maxByOrNull { city ->
            val cityCenterRent = city.realEstatesPrices.apartment3BedroomsInCityCentre!!
            val outsideCenterRent = city.realEstatesPrices.apartment3BedroomsOutsideOfCentre!!
            cityCenterRent - outsideCenterRent
        }

        // Return the city with the highest rent difference or throw an exception if no valid city is found
        return cityWithHighestRentDifference ?: throw NoSuchElementException("No city found with valid data")
    }
}
