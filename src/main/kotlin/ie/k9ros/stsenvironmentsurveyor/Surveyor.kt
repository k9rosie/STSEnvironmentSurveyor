package ie.k9ros.stsenvironmentsurveyor

import basemod.BaseMod
import basemod.BaseMod.logger
import basemod.interfaces.PostInitializeSubscriber
import basemod.interfaces.PreUpdateSubscriber
import com.badlogic.gdx.controllers.Controllers
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.CardCrawlGame.trial
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.CardHelper
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.helpers.SeedHelper
import com.megacrit.cardcrawl.helpers.controller.CInputHelper
import com.megacrit.cardcrawl.random.Random
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen
import com.megacrit.cardcrawl.shop.ShopScreen
import ie.k9ros.stsenvironmentsurveyor.controller.WebController
import ie.k9ros.stsenvironmentsurveyor.web.ServerConfiguration
import ie.k9ros.stsenvironmentsurveyor.web.WebServer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

@SpireInitializer
class Surveyor : PostInitializeSubscriber, PreUpdateSubscriber {
    companion object {
        val webController = WebController()
        var newRun = false

        // score things
        var totalPlayerTakenDamage = 0
        var totalPlayerDealtDamage = 0

        lateinit var serverConfig: ServerConfiguration
        lateinit var server: WebServer

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
                ((totalPlayerDealtDamage * 0.01) - (totalPlayerTakenDamage * 0.01))
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

    override fun receivePostInitialize() {
        Controllers.getControllers().add(webController)
        CInputHelper.initializeIfAble()
    }

    override fun receivePreUpdate() {
        if (newRun) {
            newRun()
            newRun = false
        }
    }

    private fun newRun() {
        totalPlayerTakenDamage = 0
        totalPlayerDealtDamage = 0
        val playerWithSave = CardCrawlGame.characterManager.loadChosenCharacter()
        if (playerWithSave != null) {
            SaveAndContinue.deleteSave(playerWithSave)
        }
        AbstractDungeon.reset()

        AbstractRelic.relicPage = 0
        ModHelper.setModsFalse()
        SeedHelper.cachedSeed = null
        Settings.seed = null
        Settings.seedSet = false
        Settings.specialSeed = null
        Settings.isTrial = false
        Settings.isDailyRun = false
        Settings.isEndless = false
        Settings.isFinalActAvailable = false
        Settings.hasRubyKey = false
        Settings.hasEmeraldKey = false
        Settings.hasSapphireKey = false
        CustomModeScreen.finalActAvailable = false
        trial = null
        ShopScreen.resetPurgeCost()
        CardHelper.clear()

        val sourceTime = System.nanoTime()
        val rng = Random(sourceTime)
        Settings.seedSourceTimestamp = sourceTime
        Settings.seed = SeedHelper.generateUnoffensiveSeed(rng)
        Settings.seedSet = false
        ModHelper.setModsFalse()
        AbstractDungeon.generateSeeds()
        AbstractDungeon.isAscensionMode = false
        AbstractDungeon.ascensionLevel = 0
        CardCrawlGame.chosenCharacter = AbstractPlayer.PlayerClass.IRONCLAD
        AbstractDungeon.player = CardCrawlGame.characterManager.recreateCharacter(CardCrawlGame.chosenCharacter)
        CardCrawlGame.mode = CardCrawlGame.GameMode.GAMEPLAY
        CardCrawlGame.nextDungeon = "Exordium"
        CardCrawlGame.dungeonTransitionScreen = DungeonTransitionScreen("Exordium")
        CardCrawlGame.monstersSlain = 0
        CardCrawlGame.elites1Slain = 0
        CardCrawlGame.elites2Slain = 0
        CardCrawlGame.elites3Slain = 0
        AbstractDungeon.floorNum = 0
    }
}
