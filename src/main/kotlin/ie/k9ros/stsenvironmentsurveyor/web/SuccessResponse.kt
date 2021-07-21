package ie.k9ros.stsenvironmentsurveyor.web

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(val success: Boolean = false, val error: String? = null)
