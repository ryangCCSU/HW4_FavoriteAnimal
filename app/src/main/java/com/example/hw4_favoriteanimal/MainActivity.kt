package com.example.hw4_favoriteanimal

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load animal overview fragment into main container
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, AnimalOverview())
            .addToBackStack(null)
            .commit()

        //Load animal specific fragment into rating container if in landscape mode
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager.beginTransaction()
                .replace(R.id.rating_container, AnimalSpecific())
                .addToBackStack(null)
                .commit()
        }
    }
}