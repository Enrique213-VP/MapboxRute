package com.svape.mapboxroute.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.svape.mapboxroute.R

class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        supportActionBar?.hide()
    }
}