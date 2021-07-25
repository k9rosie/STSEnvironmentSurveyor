package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.RestRoom
import com.megacrit.cardcrawl.screens.GameOverScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val currentScreen: Double = -1.0,
    val actionPhase: Double = -1.0,
    val currentAction: Double = -1.0,
    val floor: Double = -1.0,
    val act: Double = -1.0,
    val actBoss: Double = -1.0,
    val ascension: Double = -1.0,
    val isAscension: Double = -1.0,
    val isInGame: Double = -1.0,
    val isVictory: Double = -1.0,
    val isGameOver: Double = -1.0,
    val player: Player = Player(),
    val room: Room = Room(),
    val shop: Shop = Shop(),
    val rest: Rest = Rest(),
    val currentMapNode: MapNode = MapNode(),
    val cardGrid: CardGrid = CardGrid(),
    val handCardSelection: HandCardSelection = HandCardSelection(),
    val map: List<List<MapNode>> = bounded(16, bounded(8, MapNode())),
)

fun getGameState(): GameState = GameState(
    normalize(AbstractDungeon.screen?.ordinal ?: -1),
    normalize(AbstractDungeon.actionManager?.phase?.ordinal ?: -1),
    normalize(AbstractDungeon.actionManager?.currentAction?.actionType?.ordinal ?: -1),
    normalize(AbstractDungeon.floorNum),
    normalize(AbstractDungeon.actNum),
    normalize(AbstractDungeon.ascensionLevel),
    hashed(AbstractDungeon.bossKey),
    toDouble(AbstractDungeon.isAscensionMode),
    toDouble(AbstractDungeon.isPlayerInDungeon()),
    toDouble(GameOverScreen.isVictory),
    toDouble(GameOverScreen.isVictory || (AbstractDungeon.player?.isDead ?: false)),
    getPlayer(AbstractDungeon.player),
    getRoom(AbstractDungeon.currMapNode?.room),
    getShop(AbstractDungeon.shopScreen),
    if (AbstractDungeon.currMapNode?.room is RestRoom?) getRest(AbstractDungeon.currMapNode?.room as RestRoom?) else Rest(),
    getMapNode(AbstractDungeon.currMapNode),
    getCardGrid(AbstractDungeon.gridSelectScreen),
    getHardCardSelection(AbstractDungeon.handCardSelectScreen),
    getBoxedMapArray(AbstractDungeon.map)
)
