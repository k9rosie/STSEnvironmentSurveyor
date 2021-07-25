package ie.k9ros.stsenvironmentsurveyor

import basemod.BaseMod
import basemod.interfaces.ISubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import ie.k9ros.stsenvironmentsurveyor.web.WebServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@SpireInitializer
class Surveyor : ISubscriber {
    companion object {
        var logger: Logger = LogManager.getLogger(Surveyor::class)
        // score things
        var totalPlayerTakenDamage = 0
        var totalPlayerDealtDamage = 0
        var invalidCommands = 0

        var hostname = System.getenv("SURVEYOR_HOSTNAME") ?: "0.0.0.0"
        var port = Integer.parseInt(System.getenv("SURVEYOR_PORT") ?: "8008")

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

            return ((currentFloor + ((currentAct - 1) * 16)) * 1) +
                (accumulatedGold * 0.01) +
                (monstersSlain * 0.1) +
                (elitesSlain * 0.1) +
                ((totalPlayerDealtDamage * 0.01) - (totalPlayerTakenDamage * 0.01)) +
                (invalidCommands * -0.001)
        }
    }

    init {
        server = WebServer(hostname, port)

        BaseMod.subscribe(this)
    }
}
