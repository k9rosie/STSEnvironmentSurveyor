package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.AbstractRoom
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val phase: Double = -1.0,
    val type: Double = -1.0,
    val event: Event = Event(),
    val isBattleOver: Double = -1.0,
    val isCombatEvent: Double = -1.0,
    val rewards: List<Reward> = bounded(10, Reward()),
    val bossRelicRewards: List<Relic> = bounded(3, Relic()),
    val monsters: List<Monster> = bounded(10, Monster()),
    val blankSpace: Double = 1.0,
)

fun getRoom(room: AbstractRoom?) = room?.let {
    Room(
        normalize(room.phase?.ordinal ?: -1),
        hashed(room.javaClass.simpleName),
        getEvent(room.event),
        toDouble(room.isBattleOver),
        toDouble(room.combatEvent),
        boundedRewardArray(
            if (AbstractDungeon.combatRewardScreen?.rewards?.size == 0)
                room.rewards
            else AbstractDungeon.combatRewardScreen.rewards,
            10
        ),
        boundedRelicArray(AbstractDungeon.bossRelicScreen?.relics, 3),
        boundedMonsterArray(room.monsters?.monsters, 10),
        0.0
    )
} ?: Room()
