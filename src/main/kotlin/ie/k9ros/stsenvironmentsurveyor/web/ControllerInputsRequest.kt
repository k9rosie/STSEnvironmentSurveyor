package ie.k9ros.stsenvironmentsurveyor.web

import kotlinx.serialization.Serializable

@Serializable
data class ControllerInputsRequest(val inputs: List<Int>)
