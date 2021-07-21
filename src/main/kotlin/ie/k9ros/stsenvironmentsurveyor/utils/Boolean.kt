package ie.k9ros.stsenvironmentsurveyor.utils

fun toInt(bool: Boolean?) = if (bool != null) if (bool) 1 else 0 else -1
