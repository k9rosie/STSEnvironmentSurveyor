package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.RestRoom
import com.megacrit.cardcrawl.screens.GameOverScreen
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val currentScreen: Int = -1,
    val actionPhase: Int = -1,
    val currentAction: Int = -1,
    val floor: Int = -1,
    val act: Int = -1,
    val actBoss: Int = -1,
    val ascension: Int = -1,
    val isAscension: Int = -1,
    val isInGame: Int = -1,
    val isVictory: Int = -1,
    val isGameOver: Int = -1,
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
    AbstractDungeon.screen?.ordinal ?: -1,
    AbstractDungeon.actionManager?.phase?.ordinal ?: -1,
    AbstractDungeon.actionManager?.currentAction?.actionType?.ordinal ?: -1,
    AbstractDungeon.floorNum,
    AbstractDungeon.actNum,
    AbstractDungeon.ascensionLevel,
    hashed(AbstractDungeon.bossKey),
    toInt(AbstractDungeon.isAscensionMode),
    toInt(AbstractDungeon.isPlayerInDungeon()),
    toInt(GameOverScreen.isVictory),
    toInt(GameOverScreen.isVictory || (AbstractDungeon.player?.isDead ?: false)),
    getPlayer(AbstractDungeon.player),
    getRoom(AbstractDungeon.currMapNode?.room),
    getShop(AbstractDungeon.shopScreen),
    if (AbstractDungeon.currMapNode?.room is RestRoom?) getRest(AbstractDungeon.currMapNode?.room as RestRoom?) else Rest(),
    getMapNode(AbstractDungeon.currMapNode),
    getCardGrid(AbstractDungeon.gridSelectScreen),
    getHardCardSelection(AbstractDungeon.handCardSelectScreen),
    getBoxedMapArray(AbstractDungeon.map)
)
