package com.ferit.streetcourts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ratinglistadapter(private val items: List<Ratings>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RatingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ratinglist, parent, false))
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is RatingViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int
    { return items.size }

    class RatingViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val ImePrezime: TextView =
            itemView.findViewById(R.id.imeprezimetext)
        private val RatingText: TextView =
            itemView.findViewById(R.id.revtext)
        private val RatingStar: RatingBar =
            itemView.findViewById(R.id.revbar)


        fun bind(rating: Ratings) {

            ImePrezime.text = rating.ImePrezime
            RatingText.text = rating.RatingText
            RatingStar.rating = rating.RatingStar.toString().toFloat()




        }


    }

}

