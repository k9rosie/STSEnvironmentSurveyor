package ie.k9ros.stsenvironmentsurveyor

import basemod.BaseMod
import basemod.interfaces.ISubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import ie.k9ros.stsenvironmentsurveyor.web.ServerConfiguration
import ie.k9ros.stsenvironmentsurveyor.web.WebServer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.IOException

@SpireInitializer
class Surveyor : ISubscriber {
    companion object {
        var logger: Logger = LogManager.getLogger(Surveyor::class)
        // score things
        var totalPlayerTakenDamage = 0
        var totalPlayerDealtDamage = 0
        var invalidCommands = 0
        var proceeds = 0

        lateinit var serverConfig: ServerConfiguration
        lateinit var server: WebServer

        var seeds = arrayOf(
            "the", "history", "of", "all", "hitherto", "existing", "societies", "is",
            "class", "struggle"
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

            return (currentFloor * 0.1) +
                ((currentAct - 1)) +
                (accumulatedGold * 0.01) +
                (monstersSlain * 0.1) +
                (elitesSlain * 0.1) +
                ((totalPlayerDealtDamage * 0.01) - (totalPlayerTakenDamage * 0.01)) +
                (invalidCommands * -0.01) +
                    (proceeds * 0.01)
        }
    }

    init {
        serverConfig = try {
            val fileConfig = File("config.json")
            Json.decodeFromString(fileConfig.readText())
        } catch (e: IOException) {
            logger.warn("Exception: $e")
            ServerConfiguration("0.0.0.0", 8008)
        }

        server = WebServer(serverConfig.hostname, serverConfig.port)

        BaseMod.subscribe(this)
    }
}
