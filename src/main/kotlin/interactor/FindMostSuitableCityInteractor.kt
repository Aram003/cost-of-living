package interactor

import model.CityEntity
import datasource.CostOfLivingDataSource

class FindMostSuitableCityInteractor(
    private val dataSource: CostOfLivingDataSource
) {

    fun execute(): String {
        // Fetch city data from the data source
        val allCities = dataSource.getAllCitiesData()

        // Filter out low-quality data
        val validCities = allCities.filter { it.dataQuality }

        // Calculate total monthly expenses for the family
        val monthlyExpenses = calculateMonthlyExpenses()

        // Calculate father's monthly salary based on the city's average salary
        val fatherSalary = validCities.map { it.salaryStatistics.averageSalaryFullTimeJob }.average() * 2

        // Calculate the amount of savings the family can make each month
        val savings = fatherSalary - monthlyExpenses - 250 // Subtract $250 for other needs limit

        // Find the city where the family can have the highest savings
        val mostSuitableCity = validCities.maxByOrNull { city ->
            val rent = city.realEstatesPrices.rentThreeBedroomOutsideCityCenter
            rent?.let { savings - it } ?: Double.MIN_VALUE
        }

        return mostSuitableCity?.cityName ?: "No suitable city found"
    }

    private fun calculateMonthlyExpenses(): Double {
        // Define the monthly consumption of the family
        val whiteBread = 15 * 2 // 2 people
        val cheese = 1 * 4 // 4 people
        val beef = 4 * 4 // 4 people
        val chickenFillets = 10 * 4 // 4 people
        val rice = 2 * 4 // 4 people

        // Calculate total monthly expenses
        return (whiteBread + cheese + beef + chickenFillets + rice) * 10 // Assuming monthly prices
    }
}
