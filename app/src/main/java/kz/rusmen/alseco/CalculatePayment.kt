package kz.rusmen.alseco

import kotlin.math.round

fun CalculatePayment(last: Double, prev: Double, rate: Double): Double {
    var payment = (last - prev) * rate
    payment = round(payment * 100) / 100

    return payment
}