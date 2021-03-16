package com.example.covid19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.entity.Province
import kotlinx.android.synthetic.main.row2.view.*


class CovidInfoAdapter(): RecyclerView.Adapter<CovidInfoAdapter.MyViewHolder>()  {

    private var provinceList:List<Province> = emptyList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidInfoAdapter.MyViewHolder {
        return CovidInfoAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row2, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return provinceList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.region_name.text = provinceList.get(position).province
        holder.itemView.active_num.text = provinceList.get(position).active.toString()
        holder.itemView.deaths_num.text = provinceList.get(position).deaths.toString()
        holder.itemView.confirmed_num.text = provinceList.get(position).deaths.toString()
        holder.itemView.recovered_num.text = provinceList.get(position).deaths.toString()
    }

    fun  setData(provinceList: List<Province>){
        this.provinceList = provinceList
        notifyDataSetChanged()
    }

}