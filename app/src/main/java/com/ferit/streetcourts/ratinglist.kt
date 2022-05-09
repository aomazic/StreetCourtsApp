package com.ferit.streetcourts

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ratinglist : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            getSupportActionBar()?.setDisplayShowTitleEnabled(false)
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setContentView(R.layout.activity_main)
        val position = intent.extras!!.getString("KEY_MESSAGE5")
        val lista = findViewById <RecyclerView> (R.id.ratinglista)
        db.collection("/Tereni/$position/Ratings")
            .addSnapshotListener { value, error ->
                if (error == null) {
                    val values: List <Ratings> = value!!.toObjects(Ratings::class.java)
                    lista.apply {
                        layoutManager =
                            LinearLayoutManager(this@ratinglist)
                        adapter = ratinglistadapter(values)
                    }
                } else {
                    Log.e("FIRESTORE ERROR", error.message.toString())
                }



            }
        val itemDecor = DividerItemDecoration(this, HORIZONTAL)
        lista.addItemDecoration(itemDecor)


    }
    override fun onBackPressed() {
        val position = intent.extras!!.getString("KEY_MESSAGE5")
        val docRef = db.collection("Tereni").document("$position")
        docRef.get()
            .addOnSuccessListener { document ->
                val teren = document.toObject<Teren>()
                val brrating = teren!!.BrRating
                val latLng : com.google.android.gms.maps.model.LatLng =
                    com.google.android.gms.maps.model.LatLng(
                        teren.lat.toString().toDouble(),
                        teren.long.toString().toDouble()
                    )

                val displayIntent = Intent(this, mapstwo::class.java)
                displayIntent.putExtra("KEY_MESSAGE1", "$position")
                displayIntent.putExtra("KEY_MESSAGE2", "$brrating")
                val args = Bundle()
                args.putParcelable("KEY_MESSAGE3", latLng)
                displayIntent.putExtra("bundle" , args)
                Log.d(ContentValues.TAG, "No such documasdčpkmasdčlkasmčoakdmlčkasmdlčakmdlaskdmalčdmčlkmdčlkasmdčklamdčlkasmdčskaloment $latLng")
                startActivity(displayIntent)
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


    }

}