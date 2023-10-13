package com.livmas.vtb_hack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point

class MapObjectHolder: ViewModel() {
    val userLocation: MutableLiveData<Point> by lazy {
        MutableLiveData<Point>()
    }
}