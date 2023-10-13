package com.livmas.vtb_hack.object_creaters

import android.graphics.Color
import android.util.Log
import com.livmas.vtb_hack.MapObjectsHolder
import com.livmas.vtb_hack.enums.MyObjects
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.FilterVehicleTypes
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.mapkit.transport.masstransit.TransitOptions
import com.yandex.runtime.Error

class WalkingRouter(private val objects: MapObjectCollection, private val holder: MapObjectsHolder): Session.RouteListener {
    private val router = TransportFactory.getInstance().createMasstransitRouter()
    private val animator = PolylineAnimator(objects, holder, PolylineAnimator.PolylineAttrs(
        Color.CYAN,
        4f,
        Color.TRANSPARENT,
        0f,
        12.5f,
        5f
    )
    )
    private val tag = "route"

    override fun onMasstransitRoutes(p0: MutableList<Route>) {
        val polylineMP = objects.addPolyline(p0[0].geometry)
        holder.addObject(MyObjects.ROUTE, polylineMP)

        polylineMP.setStrokeColor(Color.TRANSPARENT)
        animator.showPolyline(p0[0].geometry.points)
    }

    override fun onMasstransitRoutesError(p0: Error) {
        Log.e(tag, p0.toString())
    }

    fun createWalkingRoute(start: Point, end: Point) {
        val points = ArrayList<RequestPoint>()
        val options = TransitOptions(FilterVehicleTypes.NONE.value, TimeOptions())

        points.add(RequestPoint(
            start, RequestPointType.WAYPOINT, null, null))
        points.add(RequestPoint(
            end, RequestPointType.WAYPOINT, null, null))
        router.requestRoutes(points, options, this)
    }
}