package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.AbstractRoom
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val phase: Int = -1,
    val type: Int = -1,
    val event: Event = Event(),
    val isBattleOver: Int = -1,
    val isCombatEvent: Int = -1,
    val rewards: List<Reward> = bounded(10, Reward()),
    val bossRelicRewards: List<Relic> = bounded(3, Relic()),
    val monsters: List<Monster> = bounded(10, Monster()),
    val blankSpace: Int = 1,
)

fun getRoom(room: AbstractRoom?) = room?.let {
    Room(
        room.phase?.ordinal ?: -1,
        hashed(room.javaClass.simpleName),
        getEvent(room.event),
        toInt(room.isBattleOver),
        toInt(room.combatEvent),
        boundedRewardArray(
            if (AbstractDungeon.combatRewardScreen?.rewards?.size == 0)
                room.rewards
            else AbstractDungeon.combatRewardScreen.rewards
        ),
        boundedRelicArray(AbstractDungeon.bossRelicScreen?.relics, 3),
        boundedMonsterArray(room.monsters?.monsters),
        0
    )
} ?: Room()
