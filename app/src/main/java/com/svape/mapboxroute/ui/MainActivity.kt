package com.svape.mapboxroute.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.plugin.locationcomponent.*
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.svape.mapboxroute.R
import com.svape.mapboxroute.core.BaseActivity
import com.svape.mapboxroute.core.LocationPermissionHelper
import com.svape.mapboxroute.data.database.entitie.SaveLocationEntity
import com.svape.mapboxroute.databinding.ActivityMainBinding
import com.svape.mapboxroute.ui.favorite.FavoritePlacesActivity
import com.svape.mapboxroute.ui.location.LocationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity(), OnMapClickListener, OnMapLongClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var mapView: MapView
    private lateinit var dialogMap: AlertDialog.Builder
    private lateinit var ValuePointAnimation: ValueAnimator
    private lateinit var viewAnnotationManager: ViewAnnotationManager
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var floatingActionButtonUser: FloatingActionButton
    private lateinit var floatingActionButtonMap: FloatingActionButton
    private var listLocations = arrayListOf<Point>()
    private var listAnimationLocations = arrayListOf<Point>()
    private var listName = arrayListOf<String>()

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val longitude = activityResult.data?.getDoubleExtra("longitude", -74.0833439552)
                val latitude = activityResult.data?.getDoubleExtra("latitude", 4.598369421147822)
                val name = activityResult.data?.getStringExtra("name")
                if (longitude != null && latitude != null && name != null) {
                    addAnnotationToMap(longitude, latitude, name)
                }
                binding.mapView.getMapboxMap().setCamera(
                    CameraOptions.Builder().center(Point.fromLngLat(longitude!!, latitude!!))
                        .build()
                )
            } else {
                onMapReady()
            }
        }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = findViewById(R.id.mapView)
        var contMap = 0

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        binding.apply {
            mapView.getMapboxMap().apply {
                addOnMapLongClickListener(this@MainActivity)
                addOnMapClickListener(this@MainActivity)
            }
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)


            navView.setNavigationItemSelectedListener { it ->
                when (it.itemId) {
                    R.id.listPoints -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Descubre tu futuro viaje!",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@MainActivity, LocationActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.favPlaces -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Tus favoritos!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        val intent =
                            Intent(
                                this@MainActivity,
                                FavoritePlacesActivity::class.java
                            ).also {
                                it.putStringArrayListExtra("LIST_FAV_NAME", listName)
                                it.putExtra("LIST_FAV_POSITION", listLocations)
                                goToFavoriteLocations()
                            }
                    }
                    R.id.howAddMarker -> {
                        dialogMap = AlertDialog.Builder(this@MainActivity)
                        dialogMap.setTitle("Como añadir un lugar")
                            .setMessage(getString(R.string.message_put_marker))
                            .setPositiveButton("Vale") { dialog, id ->
                            }
                            .show()
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START, false)
                true
            }
        }

        floatingActionButtonUser = findViewById(R.id.navigationUser)
        floatingActionButtonUser.setOnClickListener {
            onMapReady()
        }
        floatingActionButtonMap = findViewById(R.id.changeMap)
        floatingActionButtonMap.setOnClickListener {
            contMap += 1

            if (contMap > 5) contMap = 1
            when (contMap) {
                1 -> mapView.getMapboxMap().loadStyleUri(Style.DARK)
                2 -> mapView.getMapboxMap().loadStyleUri(Style.OUTDOORS)
                3 -> mapView.getMapboxMap().loadStyleUri(Style.SATELLITE_STREETS)
                4 -> mapView.getMapboxMap().loadStyleUri(Style.SATELLITE)
                5 -> mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_NIGHT)
            }
        }
    }

    private fun addAnnotationToMap(long: Double, lat: Double, name: String) {
        bitmapFromDrawableRes(
            this,
            R.drawable.red_marker
        )?.let {
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(long, lat))
                .withIconImage(it)
                .withTextField(name)
                .withTextColor(getColor(R.color.white))
                .withTextSize(20.5)
            pointAnnotationManager.create(pointAnnotationOptions)
        }
        Log.d("Listafavo", "$listLocations , $listName")
    }

    private fun addAnnotationToMapPulsing(long: Double, lat: Double, name: String) {
        bitmapFromDrawableRes(
            this,
            R.drawable.marker_purple
        )?.let {
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(long, lat))
                .withIconImage(it)
                .withTextField(name)
                .withTextColor(getColor(R.color.white))
                .withTextSize(20.5)
            pointAnnotationManager.create(pointAnnotationOptions)
        }
        Log.d("Listafavo", "$listLocations , $listName")
    }


    private fun onMapReady() {
        addAnnotationToMap(-74.0833439552, 4.598369421147822, "Bogota")
        addAnnotationToMap(-75.27496409801941, 4.480763954968083, "Ibague")
        addAnnotationToMap(-75.61316341608345, 6.256808292282173, "Medallo")
        addAnnotationToMapPulsing(-74.0833439552, 5.598369421147822, "Near bogota")

        viewAnnotationManager = binding.mapView.viewAnnotationManager

        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(12.0)
                .build()
        )

        initLocationComponent()
        setupGesturesListener()
        animateCameraDelayed()
        //test
        pulsingLayerColor(74.0, 5.0, "ss")
    }

    private fun pulsingLayerColor(long: Double, lat: Double, name: String) {

        var pointLayer = ValueAnimator.ofObject(
            ArgbEvaluator(),
            Color.parseColor("#ec8a8a"), // Brighter shade
            Color.parseColor("#de3232") // Darker shade
        )

        pointLayer.setDuration(1000)
        pointLayer.setRepeatCount(ValueAnimator.INFINITE)
        pointLayer.setRepeatMode(ValueAnimator.REVERSE)


    }

    private fun goToFavoriteLocations(){
        val intent = Intent(this, FavoritePlacesActivity::class.java)
        responseLauncher.launch(intent)
    }


    fun onCameraTrackingDismissed() {
        mapView.location.removeOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        mapView.location.removeOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )

        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }


    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location

        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.pulsingEnabled = true
            this.pulsingMaxRadius = 80.0F
            this.pulsingColor = Color.parseColor("#FF3F51B5")
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_puck_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    com.mapbox.maps.R.drawable.mapbox_user_icon_shadow,
                ),
                scaleExpression = Expression.interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun animateCameraDelayed() {
        mapView.camera.apply {
            val bearing =
                createBearingAnimator(CameraAnimatorOptions.cameraAnimatorOptions(-45.0)) {
                    duration = 400
                    interpolator = AccelerateDecelerateInterpolator()
                }
            val zoom = createZoomAnimator(
                CameraAnimatorOptions.cameraAnimatorOptions(14.0) {
                    startValue(3.0)
                }
            ) {
                duration = 400
                interpolator = AccelerateDecelerateInterpolator()
            }
            val pitch = createPitchAnimator(
                CameraAnimatorOptions.cameraAnimatorOptions(40.0) {
                    startValue(0.0)
                }
            ) {
                duration = 400
                interpolator = AccelerateDecelerateInterpolator()
            }
            playAnimatorsSequentially(zoom, pitch, bearing)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("Request map")
    override fun onMapLongClick(point: Point): Boolean {
        var dialog = AlertDialog.Builder(this)
        var editText = EditText(this)
        editText.hint = getString(R.string.put_name_location)
        dialog.setView(editText)
        dialog.setTitle(getString(R.string.title_put_name))
        dialog.setPositiveButton(getString(R.string.acept)) { dialog, id ->
            var textEdit = editText.editableText.toString()
            listLocations.add(point)
            listName.add(textEdit.toString())
            //DataBase
            val db = SaveLocationEntity(this@MainActivity)
            db.insertLocation(textEdit, point.longitude(), point.latitude())
            addAnnotationToMap(point.longitude(), point.latitude(), textEdit)
        }
        dialog.setNegativeButton(getString(R.string.cancel)) { dialog, id ->
            dialog.cancel()
        }
        var alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.setCancelable(false)
        return true
    }

    @SuppressLint("Request map")
    override fun onMapClick(point: Point): Boolean {
        return false
    }


    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }


    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }
}