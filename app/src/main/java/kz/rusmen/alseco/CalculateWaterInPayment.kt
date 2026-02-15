package kz.rusmen.alseco

import kotlin.math.round

fun CalculateWaterInPayment(last: Double, prev: Double, rates: String): Double {
    val (r1, r2, r3, r4) = rates.split(" ")
    val rate1 = r1.toDoubleOrNull() ?: 0.0
    val rate2 = r2.toDoubleOrNull() ?: 0.0
    val rate3 = r3.toDoubleOrNull() ?: 0.0
    val rate4 = r4.toDoubleOrNull() ?: 0.0

    val volume = last - prev
    var payment = 0.0

    when {
        volume <= 12.0 -> {
            var sum = volume * rate1
            payment += sum
        }
        volume in 13.0..20.0 -> {
            payment += round(12.0 * rate1 * 100) / 100

            var sum = (volume - 12.0) * rate2
            payment += sum
        }
        volume in 21.0..40.0 -> {
            payment += round(12.0 * rate1 * 100) / 100
            payment += round(8.0 * rate2 * 100) / 100

            var sum = (volume - 20.0) * rate3
            payment += sum
        }
        else -> {
            payment += round(12.0 * rate1 * 100) / 100
            payment += round(8.0 * rate2 * 100) / 100
            payment += round(20.0 * rate3 * 100) / 100

            var sum = (volume - 40.0) * rate4
            payment += sum
        }
    }
    payment = round(payment * 100) / 100

    return payment
}
