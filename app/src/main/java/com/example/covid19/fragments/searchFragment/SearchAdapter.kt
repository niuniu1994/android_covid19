package com.example.covid19.fragments.searchFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R
import com.example.covid19.entity.Country
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.row1.view.*

class SearchAdapter(private val context: FragmentActivity) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>()  {

    private var countryList:List<Country> = emptyList()

    //call back function
    private lateinit var onItemClickListener: (String) -> Unit

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.MyViewHolder {
        return SearchAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row1, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title.text = countryList.get(position).name

        holder.itemView.setOnClickListener{
            this.onItemClickListener(it.title.text.toString())
        }
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setData(countryList: List<Country>){
        this.countryList = countryList
        notifyDataSetChanged()
    }
}

