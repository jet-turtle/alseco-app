package kz.rusmen.alseco

import kotlin.math.roundToInt

fun CalculateAmount(last: Double, prev: Double): Int {
    val amount = last - prev
    val amountInt = amount.roundToInt()

    return amountInt
}