package com.parag.indianicweather.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parag.indianicweather.R
import com.parag.indianicweather.datamodels.ForecastData

class ForCastItemAdapter(
    private val context: Context,
    private val items: List<ForecastData>?,
    var city: String?
) :
    RecyclerView.Adapter<ForCastItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_forcast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.textView.text =  "$city : "+item?.weather?.get(0)?.description
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_forcastv)
    }
}