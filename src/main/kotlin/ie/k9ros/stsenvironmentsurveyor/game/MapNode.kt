package ie.k9ros.stsenvironmentsurveyor.game

import com.megacrit.cardcrawl.map.MapRoomNode
import ie.k9ros.stsenvironmentsurveyor.utils.bounded
import ie.k9ros.stsenvironmentsurveyor.utils.hashed
import kotlinx.serialization.Serializable

@Serializable
data class MapNode(
    val x: Int = -1,
    val y: Int = -1,
    val icon: Int = -1,
    val blankSpace: Int = 1,
)

fun getMapNode(mapNode: MapRoomNode?) = mapNode?.let {
    MapNode(
        mapNode.x,
        mapNode.y,
        hashed(mapNode.getRoomSymbol(true)),
        0
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

