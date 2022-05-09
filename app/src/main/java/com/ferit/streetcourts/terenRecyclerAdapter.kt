package com.ferit.streetcourts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import java.text.FieldPosition
import kotlin.math.roundToInt

class terenRecyclerAdapter(private val items: List<Teren>,
                            private val listener:OnItemClickListener)
                            : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return TerenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.glitem_layout, parent, false))
     }
     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         when(holder) {
             is TerenViewHolder -> {
                 holder.bind(items[position])
             }
         }
     }

    override fun getItemCount(): Int
    { return items.size }

inner class TerenViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
    private val photoImage: ImageView =
        itemView.findViewById(R.id.imageglavniitem)
    private val adresa: TextView =
        itemView.findViewById(R.id.adresa)
    private val brojrating: TextView =
        itemView.findViewById(R.id.brojrating)
    private val naziv: TextView =
        itemView.findViewById(R.id.naziv)
    private val Rating: RatingBar =
        itemView.findViewById(R.id.ratingBar1)
    private var doc: String = "n"
    private var lat: String? = "0"
    private var long: String? = "0"
    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val latlng : LatLng  = LatLng(lat.toString().toDouble(), long.toString().toDouble())
        val position: String = doc
        val brrating: String = brojrating.text.toString()
    listener.OnItemClick(position, brrating, latlng)
    }

    fun bind(teren:Teren) {
        lat = teren.lat
        long = teren.long
        doc = teren.documentId
        adresa.text = teren.Adresa
        brojrating.text = teren.BrRating.toString()
        naziv.text = teren.Ime
        Rating.setRating(teren.UkRating.toString().toFloat())
        Glide
            .with(itemView.context)
            .load(teren.slika)
            .into(photoImage)


    }


}
 interface OnItemClickListener{
     fun OnItemClick(position: String, brrating : String, latlng: LatLng)
 }
}

