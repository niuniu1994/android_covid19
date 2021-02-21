package com.example.covid19.fragments.dashboardFragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R
import com.example.covid19.entity.Country
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.row.view.title
import kotlinx.android.synthetic.main.row1.view.*
import java.io.InputStream

class DashboardAdapter(private val context: FragmentActivity) :
    RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {

    //call back function
    private lateinit var onItemClickListener: (String) -> Unit

    private  var countryList:List<Country> = emptyList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //get image name
        val image: String = countryList.get(position).iso2.toLowerCase().plus(".png")
        //get image input stream
        val inputStream: InputStream = context.assets.open("png100px/${image}")
        val currentCountry = countryList[position]
        holder.itemView.title.text = currentCountry.name
        holder.itemView.icon.setImageBitmap(BitmapFactory.decodeStream(inputStream))

        holder.itemView.setOnClickListener{
            this.onItemClickListener(it.title.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun setCountryList(countries: List<Country>) {
        countryList = countries
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.onItemClickListener = listener
    }
}