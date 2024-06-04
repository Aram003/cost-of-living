package interactor

import model.CityEntity

class GetLowestFruitPrice(private val dataSource: CostOfLivingDataSource) {

    fun getCitiesWithLowestFruitPrices(limit: Int): List<String> {
        val citiesData = dataSource.getAllCitiesData()
        val filteredCities = mutableListOf<CityEntity>()

        for (city in citiesData) {
            if (city.averageMonthlyNetSalaryAfterTax != null && city.dataQuality) {
                filteredCities.add(city)
            }
        }

        filteredCities.sortBy { city ->
            val totalFruitVegetablePrices = city.fruitAndVegetablesPrices.run {
                listOfNotNull(apples1kg, banana1kg, oranges1kg, tomato1kg, potato1kg, onion1kg, lettuceOneHead).average()
            }
            val averageMonthlyNetSalary = city.averageMonthlyNetSalaryAfterTax ?: 0f
            totalFruitVegetablePrices / averageMonthlyNetSalary
        }

        return filteredCities.take(limit).map { it.cityName }
    }
}
