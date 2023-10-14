package com.livmas.vtb_hack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.livmas.vtb_hack.connection.HttpClient
import com.livmas.vtb_hack.databinding.ActivityMainBinding
import com.livmas.vtb_hack.enums.RouteType
import com.livmas.vtb_hack.fragments.InputFragment
import com.livmas.vtb_hack.object_creaters.Locator
import com.livmas.vtb_hack.object_creaters.Marker
import com.livmas.vtb_hack.object_creaters.TotalRouter
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    lateinit var mapkit: MapKit
    lateinit var binding: ActivityMainBinding
    lateinit var holder: MapObjectsHolder

    private lateinit var locator: Locator
    private lateinit var marker: Marker
    lateinit var router: TotalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        presetMap()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        holder = MapObjectsHolder(binding.mvMap.map.mapObjects)

        binding.mvMap.map.isNightModeEnabled = true

        locator = Locator(this)
        marker = Marker(this)
        router = TotalRouter(binding.mvMap.map.mapObjects, holder)

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
        binding.apply {
            fabRequest.setOnClickListener {
                val fragment = InputFragment()

                fragment.show(supportFragmentManager, "123")
            }

            ibCar.setOnClickListener {
                router.type = RouteType.Driving
            }
            ibWalking.setOnClickListener {
                router.type = RouteType.Walking
            }
        }
    }
}
