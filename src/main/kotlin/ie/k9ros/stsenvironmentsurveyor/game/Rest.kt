package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.rooms.CampfireUI
import com.megacrit.cardcrawl.rooms.RestRoom
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class Rest(
    val options: List<EventOption> = bounded(5, EventOption()),
    val isRested: Double = -1.0,
    val blankSpace: Double = 1.0
)

fun getRest(rest: RestRoom?) = rest?.let {
    val campfireUI = rest.campfireUI
    campfireUI?.let {
        val buttonsField = CampfireUI::class.java.getDeclaredField("buttons")
        buttonsField.isAccessible = true
        val buttons = (buttonsField.get(campfireUI) as ArrayList<*>?)?.map { button ->
            (button as AbstractCampfireOption?)
        }?.toCollection(ArrayList())
        Rest(
            boundedEventOptionArray(buttons),
            toDouble(rest.phase == AbstractRoom.RoomPhase.COMPLETE),
            0.0
        )
    } ?: Rest()
} ?: Rest()
