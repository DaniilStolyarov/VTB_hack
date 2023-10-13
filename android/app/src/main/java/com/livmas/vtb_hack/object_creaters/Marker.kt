package com.livmas.vtb_hack.object_creaters

import android.widget.Toast
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider

class Marker(private val activity: MainActivity) {
    fun putToastMark(point: Point) {
        val imageProvider = ImageProvider.fromResource(activity, R.drawable.ic_mark)
        val placemark = activity.binding.mvMap.map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(imageProvider)
            addTapListener(placemarkTapListener)
        }
        // ADD TO HOLDER
    }
    private val placemarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(
            activity,
            "Tapped the point (${point.longitude}, ${point.latitude})",
            Toast.LENGTH_SHORT
        ).show()
        true
    }
}