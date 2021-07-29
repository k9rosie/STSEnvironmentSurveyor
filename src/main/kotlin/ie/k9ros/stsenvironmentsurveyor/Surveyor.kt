package ie.k9ros.stsenvironmentsurveyor

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
import basemod.interfaces.PreUpdateSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import ie.k9ros.stsenvironmentsurveyor.game.getGameState
import ie.k9ros.stsenvironmentsurveyor.web.CommandExecuteJob
import ie.k9ros.stsenvironmentsurveyor.web.WebServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.LinkedList

@SpireInitializer
class Surveyor : PostInitializeSubscriber, PreUpdateSubscriber {
    companion object {
        var logger: Logger = LogManager.getLogger(Surveyor::class)
        // score things
        var totalPlayerTakenDamage = 0
        var totalPlayerDealtDamage = 0
        var invalidCommands = 0

        var hostname = System.getenv("SURVEYOR_HOSTNAME") ?: "0.0.0.0"
        var port = Integer.parseInt(System.getenv("SURVEYOR_PORT") ?: "8008")

        lateinit var server: WebServer

        var commandJobs = LinkedList<CommandExecuteJob>()

        var seeds = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
        )

        @JvmStatic
        fun initialize() {
            Surveyor()
        }

        fun getScore(): Double {
            val currentFloor = AbstractDungeon.floorNum
            val currentAct = AbstractDungeon.actNum
            val accumulatedGold = CardCrawlGame.goldGained
            val monstersSlain = CardCrawlGame.monstersSlain
            val elitesSlain = CardCrawlGame.elites1Slain + CardCrawlGame.elites2Slain + CardCrawlGame.elites3Slain
            val isLoss = if (getGameState().isGameOver.toInt() == 1 && getGameState().isVictory.toInt() == 0) -1 else 0
            val yaryardaze = if (getGameState().isGameOver.toInt() == 1 && getGameState().isVictory.toInt() == 1) 1 else 0 // winning

            return ((currentFloor + ((currentAct - 1) * 16)) * 1) +
                (accumulatedGold * 0.01) +
                (monstersSlain * 0.1) +
                (elitesSlain * 0.1) +
                ((totalPlayerDealtDamage * 0.01) - (totalPlayerTakenDamage * 0.01)) +
                (invalidCommands * -0.001) + isLoss + yaryardaze
        }
    }

    init {
        BaseMod.subscribe(this)
    }

    override fun receivePostInitialize() {
        server = WebServer(hostname, port)
    }

    override fun receivePreUpdate() {
        if (commandJobs.size > 0) {
            commandJobs.pop().execute()
        }
    }
}
