package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rewards.RewardItem
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Reward(
    val type: Int = -1,
    val gold: Int = -1,
    val relic: Relic = Relic(),
    val potion: Potion = Potion(),
    val isDoneChoosing: Int = -1,
    val cards: List<Card> = bounded(3, Card()),
    val blankSpace: Int = 1
)

fun getReward(reward: RewardItem?) = reward?.let {
    Reward(
        it.type?.ordinal ?: -1,
        it.goldAmt + it.bonusGold,
        getRelic(it.relic),
        getPotion(it.potion),
        toInt(it.isDone),
        boundedCardArray(it.cards, 3),
        0
    )
} ?: Reward()

fun boundedRewardArray(content: ArrayList<RewardItem?>?, maxSize: Int = 10) = bounded(maxSize, Reward(), content) { getReward(it) }

fun getRewards() = AbstractDungeon.combatRewardScreen?.rewards?.let { boundedRewardArray(it) } ?: bounded(10, Reward())
