package ie.k9ros.stsenvironmentsurveyor.utils

fun normalize(num: Int): Double = if (num == 0) 0.0 else 1 / num.toDouble()
