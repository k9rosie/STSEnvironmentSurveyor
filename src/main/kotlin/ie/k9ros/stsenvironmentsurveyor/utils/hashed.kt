package ie.k9ros.stsenvironmentsurveyor.utils

fun hashed(str: String?): Double = normalize(str?.hashCode() ?: 0)
