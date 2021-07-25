package ie.k9ros.stsenvironmentsurveyor.utils

fun toDouble(bool: Boolean?): Double = if (bool != null) if (bool) 1.0 else 0.0 else -1.0
