package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.toInt
import kotlinx.serialization.Serializable

@Serializable
data class EventOption(
    val text: Int = -1,
    val index: Int = -1,
    val isDisabled: Int = -1,
    val blankSpace: Int = 1,
)

fun getEventOption(option: AbstractCampfireOption?) =
    option?.let {
        val labelField = AbstractCampfireOption::class.java.getDeclaredField("label")
        labelField.isAccessible = true
        val label = labelField.get(option) as String?
        EventOption(
            hashed(label),
            0,
            toInt(!option.usable),
            0
        )
    } ?: EventOption()

fun getEventOption(option: LargeDialogOptionButton?) =
    option?.let {
        EventOption(
            hashed(it.msg),
            it.slot,
            toInt(it.isDisabled),
            0
        )
    } ?: EventOption()

fun boundedEventOptionArray(content: ArrayList<LargeDialogOptionButton?>?, maxSize: Int = 4) = bounded(maxSize, EventOption(), content) { getEventOption(it) }
@JvmName("boundedEventOptionArray1")
fun boundedEventOptionArray(content: ArrayList<AbstractCampfireOption?>?, maxSize: Int = 5) = bounded(maxSize, EventOption(), content) { getEventOption(it) }
