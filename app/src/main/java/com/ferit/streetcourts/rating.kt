package com.ferit.streetcourts

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class rating : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        }
        val displayIntent = Intent(this, ratinglist::class.java)
        val db = Firebase.firestore
        val position = intent.extras!!.getString("KEY_MESSAGE5")
        var brrating = intent.extras!!.getString("KEY_MESSAGE6").toString().toInt()
            findViewById<Button>(R.id.cancelbut).setOnClickListener(){
                onBackPressed()
            }
            findViewById<Button>(R.id.submitbut).setOnClickListener(){
            var rez: Float = 0.0F
            val ratingnum = findViewById<RatingBar>(R.id.ratingBar2).rating
            if (ratingnum != 0.0F){
            val ImePrezime = findViewById<TextView>(R.id.ImePrezText).text.toString()
            val reviewText = findViewById<TextView>(R.id.reviewtext).text.toString()
            val docData = hashMapOf(
                "ImePrezime" to ImePrezime,
                "RatingStar" to "$ratingnum",
                "RatingText" to reviewText,
            )
            db.collection("/Tereni/$position/Ratings")
                .document()
                .set(docData)
            brrating = brrating + 1
            val docRef = db.collection("/Tereni/$position/Ratings")
            docRef.get().addOnSuccessListener{ result ->
                for (document in result) {
                    val doc = document.toObject<Ratings>().RatingStar.toString().toFloat()
                    rez = doc + rez
                }
                rez = rez  / brrating
                val brdb = db.document("/Tereni/$position")
                brdb.update("BrRating" ,brrating.toString())
                brdb.update("UkRating" ,rez.toString())
                displayIntent.putExtra("KEY_MESSAGE5", "$position")
                startActivity(displayIntent)

            }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
            }
            else Toast.makeText(this, "Rating obavezan" ,Toast.LENGTH_SHORT).show()
        }

    }
}