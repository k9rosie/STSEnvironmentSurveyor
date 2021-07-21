package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val type: Int = -1,
    val roomDialogOptions: List<EventOption> = bounded(4, EventOption()),
    val eventDialogOptions: List<EventOption> = bounded(4, EventOption()),
    val optionsSelected: List<Int> = bounded(10, -1),
    val blankSpace: Int = 1
)

fun getEvent(event: AbstractEvent?) = event?.let {
    Event(
        AbstractEvent.type?.ordinal ?: -1,
        boundedEventOptionArray(RoomEventDialog.optionList),
        boundedEventOptionArray(it.imageEventText.optionList),
        bounded(10, -1, it.optionsSelected) { opt -> opt },
        0
    )
} ?: Event()
