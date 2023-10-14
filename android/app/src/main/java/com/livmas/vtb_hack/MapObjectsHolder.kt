package com.livmas.vtb_hack

import android.util.Log
import com.livmas.vtb_hack.enums.MyObjects
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolylineMapObject
import java.lang.Exception
import java.util.EnumMap
import java.util.LinkedList

class MapObjectsHolder(private val mapObjectsColl: MapObjectCollection) {
    private val currentObjects = EnumMap<MyObjects, MapObject>(MyObjects::class.java)
    private var animation = LinkedList<PolylineMapObject>()
    private var points: List<Point> = listOf()
    var location: Point? = null
    var goal: Point? = null

    fun hasObject(obj: MyObjects): Boolean {
        return currentObjects[obj] != null
    }

    fun addObject(type: MyObjects, obj: MapObject) {
        if (currentObjects[type] != null)
            mapObjectsColl.remove(currentObjects[type]!!)
        currentObjects[type] = obj
    }

    fun removeRoute() {
        if (currentObjects[MyObjects.ROUTE] == null) {
            Log.d("route", "Nothing to cancel")
            return
        }
        try {
            mapObjectsColl.remove(currentObjects[MyObjects.ROUTE]!!)
            currentObjects.remove(MyObjects.ROUTE)
        }
        catch (e: Exception) {
            Log.e("route", e.message.toString())
        }
    }

    fun clearAnim() {
        try {
            repeat (animation.size-1) {
                mapObjectsColl.remove(animation.pop())
            }
        }
        catch (e: Exception) {
            Log.e("route", e.message.toString())
        }
    }
    fun removeAnimation() {
        try {
            repeat (animation.size) {
                mapObjectsColl.remove(animation.pop())
            }
        }
        catch (e: Exception) {
            Log.e("route", e.message.toString())
        }
    }

    fun addAnimation(line: PolylineMapObject) {
        animation.add(line)
    }
}