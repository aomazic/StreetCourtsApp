package com.ferit.streetcourts

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.ferit.streetcourts.databinding.ActivityMapstwoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.taufiqrahman.reviewratings.BarLabels
import com.taufiqrahman.reviewratings.RatingReviews
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class mapstwo : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapstwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val position = intent.extras!!.getString("KEY_MESSAGE1")
        val brrating = intent.extras!!.getString("KEY_MESSAGE2")
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            getSupportActionBar()?.setDisplayShowTitleEnabled(false)
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }






        val colors = intArrayOf(
            Color.parseColor("#FF03DAC5"),
            Color.parseColor("#FF03DAC5"),
            Color.parseColor("#FF03DAC5"),
            Color.parseColor("#FF03DAC5"),
            Color.parseColor("#FF03DAC5")
        )
        var petice : Int = 0
        var cetvorke : Int = 0
        var trojke : Int = 0
        var dvojke : Int = 0
        var jedinice : Int = 0

        binding = ActivityMapstwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val db = Firebase.firestore
        val docRef = db.collection("Tereni").document("$position")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "Do,l ansdkljnaskjdnbkjasndkjlasndkljasndlkjnasdkjnasjkdnalksjdnaslkjdnlkajsndlkjasdnlkasjdcumentSnapshot data: ${document.data}")
                    val teren = document.toObject<Teren>()
                    val docRef = db.collection("/Tereni/$position/Ratings")
                    docRef.get().addOnSuccessListener{ result ->
                        for (document in result) {
                            val doc = document.toObject<Ratings>().RatingStar.toString().toFloat()
                            if (doc >= 4.5) petice ++
                            if (doc >= 3.5 && doc < 4.5) cetvorke ++
                            if (doc >= 2.5 && doc < 3.5) trojke ++
                            if (doc >= 1.5 && doc < 2.5) dvojke ++
                            if (doc >= 0.5 && doc < 1.5) jedinice ++
                        }
                        val raters = intArrayOf(
                            petice,
                            cetvorke,
                            trojke,
                            dvojke,
                            jedinice
                        )
                        if (teren != null) {
                            findViewById<RatingReviews>(R.id.rating_reviews).createRatingBars(
                                petice+cetvorke+trojke+dvojke+jedinice,
                                BarLabels.STYPE1,
                                colors,
                                raters

                            )
                            Glide
                                .with(this)
                                .load(teren.slika)
                                .into(findViewById<ImageView>(R.id.glavnaslika))
                            val lay = findViewById<ConstraintLayout>(R.id.lay)
                            if (teren.Skolsko == true)  findViewById<TextView>(R.id.SkolskoText).text = "Skolsko"
                            else lay.removeView(findViewById<TextView>(R.id.SkolskoText))
                            if (teren.Javno == true)  findViewById<TextView>(R.id.JavnoText).text = "Javno"
                            else lay.removeView(findViewById<TextView>(R.id.JavnoText))
                            if (teren.WC == true)  findViewById<TextView>(R.id.WcText).text = "WC"
                            else lay.removeView(findViewById<TextView>(R.id.WcText))
                            if (teren.Svlacionice == true)  findViewById<TextView>(R.id.SvlacionicaText).text = "Svlačionice"
                            else lay.removeView(findViewById<TextView>(R.id.SvlacionicaText))
                            findViewById<TextView>(R.id.ImeText).text = teren.Ime
                            findViewById<TextView>(R.id.AdresaText).text = teren.Adresa
                            findViewById<TextView>(R.id.RasvjetaText).text = teren.rasvjeta
                            findViewById<TextView>(R.id.DimenzijeText).text = teren.dimenzije
                            findViewById<TextView>(R.id.PodlogaText).text = teren.podloga
                            findViewById<TextView>(R.id.BrojTerenaText).text = teren.BrojS
                            findViewById<RatingBar>(R.id.ratingBar).rating = teren.UkRating.toString().toFloat()
                            findViewById<TextView>(R.id.UkRatingText).text = BigDecimal(teren.UkRating).setScale(1, RoundingMode.HALF_UP).toString()
                            findViewById<TextView>(R.id.UkRatingBrojText).text = teren.BrRating
                            findViewById<Button>(R.id.revbut).setOnClickListener(){
                                val displayIntent = Intent(this, rating::class.java)
                                displayIntent.putExtra("KEY_MESSAGE5", "$position")
                                displayIntent.putExtra("KEY_MESSAGE6", "$brrating")
                                startActivity(displayIntent)

                            }
                            findViewById<ExtendedFloatingActionButton>(R.id.gotorev).setOnClickListener(){
                                val displayIntent = Intent(this, ratinglist::class.java)
                                displayIntent.putExtra("KEY_MESSAGE5", "$position")
                                startActivity(displayIntent)


                            }
                        }
                    }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "Error getting documents: ", exception)
                        }




                } else {
                    Log.d(TAG, "No such data")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }




    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val bundle = intent.getParcelableExtra<Bundle>("bundle")
        val fokus: LatLng = bundle!!.getParcelable("KEY_MESSAGE3")!!
        mMap.addMarker(MarkerOptions().position(fokus))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fokus))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
    }

}