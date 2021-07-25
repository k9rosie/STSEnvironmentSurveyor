package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.rewards.RewardItem
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Reward(
    val type: Double = -1.0,
    val gold: Double = -1.0,
    val relic: Relic = Relic(),
    val potion: Potion = Potion(),
    val isDoneChoosing: Double = -1.0,
    val cards: List<Card> = bounded(3, Card()),
    val blankSpace: Double = 1.0
)

fun getReward(reward: RewardItem?) = reward?.let {
    Reward(
        normalize(it.type?.ordinal ?: -1),
        normalize(it.goldAmt + it.bonusGold),
        getRelic(it.relic),
        getPotion(it.potion),
        toDouble(it.isDone),
        boundedCardArray(it.cards, 3),
        0.0
    )
} ?: Reward()

fun boundedRewardArray(content: ArrayList<RewardItem?>?, maxSize: Int) = bounded(maxSize, Reward(), content) { getReward(it) }
