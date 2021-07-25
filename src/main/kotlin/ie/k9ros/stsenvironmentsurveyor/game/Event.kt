package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val type: Double = -1.0,
    val roomDialogOptions: List<EventOption> = bounded(4, EventOption()),
    val eventDialogOptions: List<EventOption> = bounded(4, EventOption()),
    val optionsSelected: List<Double> = bounded(10, -1.0),
    val blankSpace: Double = 1.0
)

fun getEvent(event: AbstractEvent?) = event?.let {
    Event(
        normalize(AbstractEvent.type?.ordinal ?: -1),
        boundedEventOptionArray(RoomEventDialog.optionList),
        boundedEventOptionArray(it.imageEventText.optionList),
        bounded(10, -1.0, it.optionsSelected) { opt -> opt.toDouble() },
        0.0
    )
} ?: Event()
