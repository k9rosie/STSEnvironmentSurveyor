package ie.k9ros.stsenvironmentsurveyor.web

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import communicationmod.CommandExecutor
import communicationmod.GameStateListener
import communicationmod.InvalidCommandException
import ie.k9ros.stsenvironmentsurveyor.Surveyor
import ie.k9ros.stsenvironmentsurveyor.game.getGameState
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

suspend fun executeCommand(command: String): Boolean {
    Surveyor.logger.info("Current screen: ${AbstractDungeon.screen}")

    return try {
        Surveyor.logger.info("Command: $command")
        if (CommandExecutor.executeCommand(command)) {
            GameStateListener.registerCommandExecution()
            while (!GameStateListener.isWaitingForCommand()) {
                delay(1)
            }
        }

        true
    } catch (e: InvalidCommandException) {
        Surveyor.logger.info("Invalid command: $command")
        Surveyor.invalidCommands++
        false
    } catch (e: Exception) {
        Surveyor.logger.error("Exception caught while trying to execute command: $e")
        false
    }
}

fun Route.gameRoute() {
    route("/game") {
        get {
            call.respond(StateResponse(Surveyor.getScore(), getGameState()))
        }
        post {
            val action = call.receive<ActionRequest>()

            return@post try {
                withTimeout(8000L) {
                    executeCommand(action.command)
                    return@withTimeout call.respond(StateResponse(Surveyor.getScore(), getGameState()))
                }
            } catch (e: TimeoutCancellationException) {
                Surveyor.logger.error("Error executing command ${action.command}: Timeout after 8 seconds")
                return@post call.respond(StateResponse(Surveyor.getScore(), getGameState()))
            }
        }
        post("/reset") {
            if (CommandExecutor.isInDungeon()) {
                AbstractDungeon.getCurrRoom().clearEvent()
                AbstractDungeon.closeCurrentScreen()
                CardCrawlGame.startOver()
                delay(4500)
            }
            Surveyor.totalPlayerTakenDamage = 0
            Surveyor.totalPlayerDealtDamage = 0
            Surveyor.invalidCommands = 0
            executeCommand("start ironclad 0 ${Surveyor.seeds.random()}")
            call.respond(StateResponse(Surveyor.getScore(), getGameState()))
        }
    }
}
