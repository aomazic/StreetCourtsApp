package com.ferit.streetcourts

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ferit.streetcourts.databinding.ActivityMapsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.maps.model.Marker
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.firebase.firestore.ktx.toObject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,terenRecyclerAdapter.OnItemClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        }
        val db = Firebase.firestore
        db.collection("Tereni")
            .addSnapshotListener { value, error ->
                if (error == null) {
                    val values: List <Teren> = value!!.toObjects(Teren::class.java)
                    findViewById <RecyclerView> (R.id.thirdlista).apply {
                        layoutManager =
                            LinearLayoutManager(this@MapsActivity)
                        adapter = terenRecyclerAdapter(values, this@MapsActivity)
                    }
                } else {
                    Log.e("FIRESTORE ERROR", error.message.toString())
                }



            }


    }

    override fun OnItemClick(position: String , brrating : String, latLng: LatLng) {
        val displayIntent = Intent(this, mapstwo::class.java)
        displayIntent.putExtra("KEY_MESSAGE1", "$position")
        displayIntent.putExtra("KEY_MESSAGE2", "$brrating")
        val args = Bundle()
        args.putParcelable("KEY_MESSAGE3", latLng)
        displayIntent.putExtra("bundle" , args)
        Log.d(TAG, "No such documasdčpkmasdčlkasmčoakdmlčkasmdlčakmdlaskdmalčdmčlkmdčlkasmdčklamdčlkasmdčskaloment $latLng")
        startActivity(displayIntent)
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val parkpob = LatLng(45.31343347459234, 18.42050193769224 )
       val parkpobmakr = mMap.addMarker(MarkerOptions().position(parkpob).title("Nogometno igralište Park pobjede"))
        val gor = LatLng(45.31174327539492, 18.41420100672979)
       val gormark = mMap.addMarker(MarkerOptions().position(gor).title("Nogometno igralište IGK"))
        val gork = LatLng(45.31168922512942,  18.414203006397656  )
        val gorkmark = mMap.addMarker(MarkerOptions().position(parkpob).title("Košarkaško igralište IGK"))
        val naz = LatLng(45.309625616278545, 18.413945514367082 )
        val nazmark = mMap.addMarker(MarkerOptions().position(naz).title("Košarkaško igralište Nazorova"))
        val par = LatLng(45.309220371200624, 18.412480296472758 )
        val Dj = LatLng(45.310242152184934, 18.409778516481186 )
        val partizanmarker = mMap.addMarker(MarkerOptions().position(par).title("Partizan"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Dj))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
        mMap.setOnMarkerClickListener { marker ->
            val markerName = marker.title
            val markerpos = marker.position
                                    if (markerName == "Nogometno igralište Park pobjede") {
                                        val db = Firebase.firestore
                                        val docRef = db.collection("Tereni").document("EMOdke3Z2dptglP1O8zR")
                                        docRef.get()
                                            .addOnSuccessListener { document ->
                                                if (document != null) {
                                                    val teren = document.toObject<Teren>()
                                                    val displayIntent = Intent(this, mapstwo::class.java)
                                                    displayIntent.putExtra("KEY_MESSAGE1", "EMOdke3Z2dptglP1O8zR")
                                                    if (teren != null) {
                                                        displayIntent.putExtra("KEY_MESSAGE2", "${teren.BrRating}")
                                                    }
                                                    val args = Bundle()
                                                    args.putParcelable("KEY_MESSAGE3", marker.position)
                                                    displayIntent.putExtra("bundle" , args)
                                                    startActivity(displayIntent)
                                                } else {
                                                    Log.d(TAG, "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d(TAG, "get failed with ", exception)
                                            }

                                    }

                                    if (markerName == "Nogometno igralište IGK") {
                                        val db = Firebase.firestore
                                        val docRef = db.collection("Tereni").document("Q8SnGWjNDjbdUCSEWR3g")
                                        docRef.get()
                                            .addOnSuccessListener { document ->
                                                if (document != null) {
                                                    val teren = document.toObject<Teren>()
                                                    val displayIntent = Intent(this, mapstwo::class.java)
                                                    displayIntent.putExtra("KEY_MESSAGE1", "Q8SnGWjNDjbdUCSEWR3g")
                                                    if (teren != null) {
                                                        displayIntent.putExtra("KEY_MESSAGE2", "${teren.BrRating}")
                                                    }
                                                    val args = Bundle()
                                                    args.putParcelable("KEY_MESSAGE3", marker.position)
                                                    displayIntent.putExtra("bundle" , args)
                                                    startActivity(displayIntent)
                                                } else {
                                                    Log.d(TAG, "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d(TAG, "get failed with ", exception)
                                            }

                                    }


                                    if (markerName == "Košarkaško igralište IGK") {
                                        val db = Firebase.firestore
                                        val docRef = db.collection("Tereni").document("UGNf0igN2PH6gYNnl3wZ")
                                        docRef.get()
                                            .addOnSuccessListener { document ->
                                                if (document != null) {
                                                    val teren = document.toObject<Teren>()
                                                    val displayIntent = Intent(this, mapstwo::class.java)
                                                    displayIntent.putExtra("KEY_MESSAGE1", "UGNf0igN2PH6gYNnl3wZ")
                                                    if (teren != null) {
                                                        displayIntent.putExtra("KEY_MESSAGE2", "${teren.BrRating}")
                                                    }
                                                    val args = Bundle()
                                                    args.putParcelable("KEY_MESSAGE3", marker.position)
                                                    displayIntent.putExtra("bundle" , args)
                                                    startActivity(displayIntent)
                                                } else {
                                                    Log.d(TAG, "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d(TAG, "get failed with ", exception)
                                            }

                                    }


                                    if (markerName == "Košarkaško igralište Nazorova") {
                                        val db = Firebase.firestore
                                        val docRef = db.collection("Tereni").document("gkXJzMoe7ZlwHrNRFEjz")
                                        docRef.get()
                                            .addOnSuccessListener { document ->
                                                if (document != null) {
                                                    val teren = document.toObject<Teren>()
                                                    val displayIntent = Intent(this, mapstwo::class.java)
                                                    displayIntent.putExtra("KEY_MESSAGE1", "gkXJzMoe7ZlwHrNRFEjz")
                                                    if (teren != null) {
                                                        displayIntent.putExtra("KEY_MESSAGE2", "${teren.BrRating}")
                                                    }
                                                    val args = Bundle()
                                                    args.putParcelable("KEY_MESSAGE3", marker.position)
                                                    displayIntent.putExtra("bundle" , args)
                                                    startActivity(displayIntent)
                                                } else {
                                                    Log.d(TAG, "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d(TAG, "get failed with ", exception)
                                            }

                                    }


                                    if (markerName == "Partizan") {
                                        val db = Firebase.firestore
                                        val docRef = db.collection("Tereni").document("mEFF23qr4uu7gmcHL4Ka")
                                        docRef.get()
                                            .addOnSuccessListener { document ->
                                                if (document != null) {
                                                    val teren = document.toObject<Teren>()
                                                    val displayIntent = Intent(this, mapstwo::class.java)
                                                    displayIntent.putExtra("KEY_MESSAGE1", "mEFF23qr4uu7gmcHL4Ka")
                                                    if (teren != null) {
                                                        displayIntent.putExtra("KEY_MESSAGE2", "${teren.BrRating}")
                                                    }
                                                    val args = Bundle()
                                                    args.putParcelable("KEY_MESSAGE3", marker.position)
                                                    displayIntent.putExtra("bundle" , args)
                                                    startActivity(displayIntent)
                                                } else {
                                                    Log.d(TAG, "No such document")
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.d(TAG, "get failed with ", exception)
                                            }

                                    }
                                    false
                                }
    }


}