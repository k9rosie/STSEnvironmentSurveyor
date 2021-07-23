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
import kotlinx.coroutines.delay

suspend fun executeCommand(command: String): Boolean {
    return try {
        Surveyor.logger.info("Command: $command")
        if (CommandExecutor.executeCommand(command)) {
            GameStateListener.registerCommandExecution()
        }

        while (!GameStateListener.isWaitingForCommand()) {
            delay(1)
        }
        true
    } catch (e: InvalidCommandException) {
        Surveyor.logger.info("Invalid command: $command")
        Surveyor.invalidCommands++
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
            action.command.split(" ").getOrNull(0)?.let {
                if (it == "end" || it == "proceed") {
                    Surveyor.proceeds++
                }
            }
            executeCommand(action.command)
            call.respond(StateResponse(Surveyor.getScore(), getGameState()))
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
