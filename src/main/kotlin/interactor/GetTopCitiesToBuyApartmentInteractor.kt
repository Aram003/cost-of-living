package interactor

import datasource.CostOfLivingDataSource

class GetTopCitiesToBuyApartmentInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(): List<Pair<String, Double>> {
        // Fetch all city data from the data source
        val allCities = dataSource.getAllCitiesData()

        // Filter out low-quality data and cities with missing or negative apartment prices
        val validCities = allCities.filter { city ->
            city.dataQuality &&
                    city.realEstatesPrices.apartmentPricePerSquareMeter != null &&
                    city.realEstatesPrices.apartmentPricePerSquareMeter!! > 0
        }

        // Calculate time needed to save money for buying a 100-meter apartment in each city
        val citiesWithTimeToBuyApartment = validCities.map { city ->
            val fullTimeJobIncome = city.salaryStatistics.averageSalaryFullTimeJob
            val partTimeJobIncome = city.salaryStatistics.averageSalaryPartTimeJob

            val monthlySavings = fullTimeJobIncome - partTimeJobIncome

            val apartmentPricePerSquareMeter = city.realEstatesPrices.apartmentPricePerSquareMeter!!
            val apartmentPrice = apartmentPricePerSquareMeter * 100

            val yearsToBuyApartment = apartmentPrice / (monthlySavings * 12)

            city.cityName to yearsToBuyApartment
        }

        // Sort cities by the time needed to buy the apartment and take the top 10
        val sortedCities = citiesWithTimeToBuyApartment.sortedBy { (_, years) -> years }.take(10)

        // Return a list of pairs containing city names and the years needed to buy the apartment
        return sortedCities
    }
}
