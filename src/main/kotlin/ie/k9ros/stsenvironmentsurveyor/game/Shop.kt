package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.shop.ShopScreen
import com.megacrit.cardcrawl.shop.StorePotion
import com.megacrit.cardcrawl.shop.StoreRelic
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    val purgeCost: Double = -1.0,
    val isPurgeAvailable: Double = -1.0,
    val cards: List<Card> = bounded(7, Card()),
    val relics: List<Relic> = bounded(3, Relic()),
    val potions: List<Potion> = bounded(3, Potion()),
)

fun getShop(shop: ShopScreen?) = shop?.let {
    val relicsField = ShopScreen::class.java.getDeclaredField("relics")
    relicsField.isAccessible = true
    val potionsField = ShopScreen::class.java.getDeclaredField("potions")
    potionsField.isAccessible = true
    val relics = (relicsField.get(shop) as ArrayList<*>?)?.map { relic ->
        (relic as StoreRelic?)?.let {
            relic.relic
        }
    }?.toCollection(ArrayList())
    val potions = (potionsField.get(shop) as ArrayList<*>?)?.map { potion ->
        (potion as StorePotion?)?.let {
            potion.potion
        }
    }?.toCollection(ArrayList())
    Shop(
        normalize(ShopScreen.actualPurgeCost),
        toDouble(it.purgeAvailable),
        boundedCardArray(ArrayList(it.coloredCards + it.colorlessCards), 7),
        boundedRelicArray(relics, 3),
        boundedPotionArray(potions, 3)
    )
} ?: Shop()
