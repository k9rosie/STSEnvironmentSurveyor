package ie.k9ros.stsenvironmentsurveyor.web

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.concurrent.thread

class WebServer(hostname: String = "0.0.0.0", port: Int = 8008) {
    @OptIn(ExperimentalSerializationApi::class)
    var app = embeddedServer(CIO, host = hostname, port = port) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            gameRoute()
            scoreRoute()
        }
    }

    var thread = thread {
        app.start(wait = true)
    }

    init {
        thread
    }
}
