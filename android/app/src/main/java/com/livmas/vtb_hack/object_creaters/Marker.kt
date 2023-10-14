package com.livmas.vtb_hack.object_creaters

import android.widget.Toast
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

class Marker(private val activity: MainActivity) {
    fun putMark(point: Point) {
        val imageProvider = ImageProvider.fromResource(activity, R.drawable.ic_mark)
        val placemark = activity.binding.mvMap.map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(imageProvider)
            addTapListener(placemarkTapListener)
        }
        // ADD TO HOLDER
    }
    private val placemarkTapListener = MapObjectTapListener { it, _ ->
        if (activity.holder.location == null)
            return@MapObjectTapListener true

        activity.holder.goal = (it as PlacemarkMapObject).geometry
        activity.router.putDriving()
        true
    }
}