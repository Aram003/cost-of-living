package interactor

import model.CityEntity

class GetLowestFruitPrice(private val dataSource: CostOfLivingDataSource) {


    fun getCitiesWithLowestFruitPrices(limit: Int): List<String> {
      
        val citiesData = dataSource.getAllCitiesData()

   
        val filteredCities = citiesData.filter { city ->
            city.averageMonthlyNetSalaryAfterTax != null && city.dataQuality
        }

        val sortedCities = filteredCities.sortedBy { city ->
            val totalFruitVegetablePrices = listOfNotNull(
                city.fruitAndVegetablesPrices.apples1kg,
                city.fruitAndVegetablesPrices.banana1kg,
                city.fruitAndVegetablesPrices.oranges1kg,
                city.fruitAndVegetablesPrices.tomato1kg,
                city.fruitAndVegetablesPrices.potato1kg,
                city.fruitAndVegetablesPrices.onion1kg,
                city.fruitAndVegetablesPrices.lettuceOneHead
            ).average()

    
            val averageMonthlyNetSalary = city.averageMonthlyNetSalaryAfterTax ?: 0f

     
            totalFruitVegetablePrices / averageMonthlyNetSalary
        }

        return sortedCities.take(limit).map { it.cityName }
    }
}

