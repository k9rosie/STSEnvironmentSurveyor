package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.rooms.CampfireUI
import com.megacrit.cardcrawl.rooms.RestRoom
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class Rest(
    val options: List<EventOption> = bounded(5, EventOption()),
    val isRested: Int = -1,
    val blankSpace: Int = 1
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
            toInt(rest.phase == AbstractRoom.RoomPhase.COMPLETE),
            0
        )
    } ?: Rest()
} ?: Rest()