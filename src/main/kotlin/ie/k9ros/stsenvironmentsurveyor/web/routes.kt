package ie.k9ros.stsenvironmentsurveyor.web

import com.megacrit.cardcrawl.core.Settings
import ie.k9ros.stsenvironmentsurveyor.Surveyor
import ie.k9ros.stsenvironmentsurveyor.game.getGameState
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.coroutines.delay

fun Route.gameRoute() {
    route("/game") {
        get {
            val gameState = getGameState()
            call.respond(gameState)
        }
        post {
            val body = call.receive<ControllerInputsRequest>()
            Settings.isControllerMode = true
            body.inputs.forEach {
                if (it != 69) {
                    Settings.isControllerMode = true
                    Surveyor.webController.pressedButtons.add(it)
                    Surveyor.webController.notifyListenersButtonDown(it)
                    delay(20)
                    Surveyor.webController.notifyListenersButtonUp(it)
                    Surveyor.webController.pressedButtons.remove(it)
                    delay(20)
                }
            }

            call.respond(Surveyor.getScore())
        }
        // post("/action") {
        //     val body = call.receive<ActionRequest>()
        //     var response = SuccessResponse(true)
        //     try {
        //         if (body.command == "choose") print(ChoiceScreenUtils.getCurrentChoiceList())
        //         CommandExecutor.executeCommand(body.command)
        //     } catch (e: InvalidCommandException) {
        //         response = SuccessResponse(false, e.toString())
        //     }

        //     call.respond(response)
        // }
        post("/reset") {
            Surveyor.newRun = true
            call.respond(SuccessResponse(true))
        }
    }
}

fun Route.scoreRoute() {
    route("/score") {
        get {
            call.respond(Surveyor.getScore())
        }
    }
}
