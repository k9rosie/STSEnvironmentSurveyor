package ie.k9ros.stsenvironmentsurveyor.web

import kotlinx.serialization.Serializable

@Serializable
data class ActionRequest(val command: String)
