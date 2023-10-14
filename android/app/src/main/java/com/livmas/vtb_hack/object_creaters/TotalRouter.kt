package com.livmas.vtb_hack.object_creaters

import com.livmas.vtb_hack.MapObjectsHolder
import com.yandex.mapkit.map.MapObjectCollection

class TotalRouter(mapObjects: MapObjectCollection, private val holder: MapObjectsHolder) {
    private val driving: CarRouter
    private val walking: WalkingRouter

    init {
        driving = CarRouter(mapObjects, holder)
        walking = WalkingRouter(mapObjects, holder)
    }

    fun putDriving() {
        holder.goal?.let { goal ->
            holder.location?.let { loc ->
                driving.buildRoute(loc, goal)
            }
        }
    }
    fun putWalking() {
        holder.location?.let { loc ->
            holder.goal?.let { goal ->
                walking.createWalkingRoute(loc, goal)
            }
        }
    }
}