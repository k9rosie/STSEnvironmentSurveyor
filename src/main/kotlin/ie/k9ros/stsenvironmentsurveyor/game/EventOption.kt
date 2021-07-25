package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import ie.k9ros.stsenvironmentsurveyor.utils.toDouble
import kotlinx.serialization.Serializable

@Serializable
data class EventOption(
    val text: Double = -1.0,
    val index: Double = -1.0,
    val isDisabled: Double = -1.0,
    val blankSpace: Double = 1.0,
)

fun getEventOption(option: AbstractCampfireOption?) =
    option?.let {
        val labelField = AbstractCampfireOption::class.java.getDeclaredField("label")
        labelField.isAccessible = true
        val label = labelField.get(option) as String?
        EventOption(
            hashed(label),
            0.0,
            toDouble(!option.usable),
            0.0
        )
    } ?: EventOption()

fun getEventOption(option: LargeDialogOptionButton?) =
    option?.let {
        EventOption(
            hashed(it.msg),
            normalize(it.slot),
            toDouble(it.isDisabled),
            0.0
        )
    } ?: EventOption()

fun boundedEventOptionArray(content: ArrayList<LargeDialogOptionButton?>?, maxSize: Int) = bounded(maxSize, EventOption(), content) { getEventOption(it) }
@JvmName("boundedEventOptionArray1")
fun boundedEventOptionArray(content: ArrayList<AbstractCampfireOption?>?, maxSize: Int) = bounded(maxSize, EventOption(), content) { getEventOption(it) }
