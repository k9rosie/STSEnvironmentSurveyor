package ie.k9ros.stsenvironmentsurveyor.web

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfiguration(val hostname: String, val port: Int)
