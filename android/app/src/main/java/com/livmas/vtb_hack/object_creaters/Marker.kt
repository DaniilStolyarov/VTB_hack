package com.livmas.vtb_hack.object_creaters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

class Marker(private val activity: MainActivity) {
    fun putMark(point: Point, workload: Float) {

        val imageProvider = ImageProvider.fromResource(activity,
            if (workload < 35)
                R.drawable.mark_green
            else if (workload < 80)
                R.drawable.mark_yellow
            else
                R.drawable.mark_red
        )
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
        activity.router.buildRoute()
        true
    }
}