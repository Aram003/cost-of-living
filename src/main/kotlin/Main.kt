import dataSource.CsvDataSource
import dataSource.utils.CsvParser
import interactor.CostOfLivingDataSource
import interactor.GetCityHasCheapestInternetConnectionInteractor
import interactor.GetHighestSalaryAverageCititesNamesInteractor
import interactor.GetLowestFruitPrice

fun main() {
    val csvParser = CsvParser()
    val dataSource: CostOfLivingDataSource = CsvDataSource(csvParser)

    val getHighestSalaryAverageCities = GetHighestSalaryAverageCititesNamesInteractor(dataSource)
    println("Cities with highest average salary:")
    println(getHighestSalaryAverageCities.execute(limit = 10))
    printSeparationLine()

    val getCityHasCheapestInternetConnectionInteractor = GetCityHasCheapestInternetConnectionInteractor(dataSource)
    println("City with cheapest internet connection:")
    println(getCityHasCheapestInternetConnectionInteractor.execute())
    printSeparationLine()

    val getLowestFruitPriceInteractor = GetLowestFruitPrice(dataSource)
    println("Cities with lowest fruit prices:")
    println(getLowestFruitPriceInteractor.getCitiesWithLowestFruitPrices(limit = 10))
}

private fun printSeparationLine() {
    println("\n_______________________________\n")
}
