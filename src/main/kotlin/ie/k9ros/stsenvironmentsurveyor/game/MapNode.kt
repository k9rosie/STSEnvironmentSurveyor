package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.map.MapRoomNode
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import ie.k9ros.stsenvironmentsurveyor.utils.normalize
import kotlinx.serialization.Serializable

@Serializable
data class MapNode(
    val x: Double = -1.0,
    val y: Double = -1.0,
    val icon: Double = -1.0,
    val blankSpace: Double = 1.0,
)

fun getMapNode(mapNode: MapRoomNode?) = mapNode?.let {
    MapNode(
        normalize(mapNode.x),
        normalize(mapNode.y),
        hashed(mapNode.getRoomSymbol(true)),
        0.0
    )
} ?: MapNode()

// 15 x 7
fun getBoxedMapArray(map: ArrayList<ArrayList<MapRoomNode?>?>?, height: Int = 16, width: Int = 8): List<List<MapNode>> =
    map?.let {
        bounded(height, bounded(width, MapNode()), it) { floor ->
            bounded(width, MapNode(), floor) { node ->
                getMapNode(node)
            }
        }
    } ?: bounded(15, bounded(7, MapNode()))
