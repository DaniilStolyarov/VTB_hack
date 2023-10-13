package com.livmas.vtb_hack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.livmas.vtb_hack.connection.HttpClient
import com.livmas.vtb_hack.databinding.ActivityMainBinding
import com.livmas.vtb_hack.object_creaters.Locator
import com.livmas.vtb_hack.object_creaters.Marker
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point

class MainActivity : AppCompatActivity() {
    lateinit var mapkit: MapKit
    lateinit var binding: ActivityMainBinding
    lateinit var holder: MapObjectsHolder

    private lateinit var locator: Locator
    private lateinit var marker: Marker

    private val client = HttpClient(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        presetMap()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        holder = MapObjectsHolder(binding.mvMap.map.mapObjects)

        binding.mvMap.map.isNightModeEnabled = true

        locator = Locator(this)
        marker = Marker(this)

        initButtons()
    }

    override fun onStart() {
        super.onStart()
        mapkit.onStart()
        binding.mvMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapkit.onStop()
        binding.mvMap.onStop()
    }

    private fun presetMap() {
        val key = BuildConfig.MAPKIT_API_KEY
        MapKitFactory.setApiKey(key)
        MapKitFactory.initialize(this)
        mapkit = MapKitFactory.getInstance()
    }

    private fun initButtons() {
        binding.fabRequest.setOnClickListener {
            client.query(Point(10.0, 23.0))
        }
    }
}
