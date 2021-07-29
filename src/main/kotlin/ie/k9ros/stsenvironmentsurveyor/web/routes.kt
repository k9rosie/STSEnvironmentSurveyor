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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.concurrent.thread

class CommandExecuteJob(private val command: String) {
    var finished = false
    var invalidCommand = false
    private var thread: Thread? = null

    // runs in the context of the main thread
    fun execute() {
        try {
            if (CommandExecutor.executeCommand(command)) {
                GameStateListener.registerCommandExecution()
                // fire coroutine to wait for this command to finish executing
                thread = thread {
                    runBlocking {
                        launch(Dispatchers.Default) {
                            wait()
                        }
                    }
                }
            } else {
                finished = true
            }
        } catch (e: InvalidCommandException) {
            Surveyor.logger.error("Invalid command: $command")
            invalidCommand = true
            finished = true
        }
    }

    private suspend fun wait() {
        while (!GameStateListener.isWaitingForCommand()) {
            delay(1)
        }
        finished = true
    }
}

suspend fun executeCommand(command: String) {
    val commandJob = CommandExecuteJob(command)
    Surveyor.commandJobs.push(commandJob)

    try {
        withTimeout(8000L) {
            while (!commandJob.finished) {
                delay(1)
            }
        }
    } catch (e: TimeoutCancellationException) {
        Surveyor.logger.error("Error executing command $command: $e")
    }

    if (commandJob.invalidCommand) Surveyor.invalidCommands++
}

fun Route.gameRoute() {
    route("/game") {
        get {
            call.respond(StateResponse(Surveyor.getScore(), getGameState()))
        }
        post {
            val action = call.receive<ActionRequest>()
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
