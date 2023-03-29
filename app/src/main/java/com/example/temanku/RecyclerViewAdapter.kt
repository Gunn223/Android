package com.example.temanku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val listdata_teman:ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    private val context:Context

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Nama :TextView
        val Alamat :TextView
        val Nohp :TextView
//        val Jkel :TextView
        val Listitem:LinearLayout

        init {
            Nama = itemView.findViewById(R.id.nama)
            Alamat = itemView.findViewById(R.id.alamat)
            Nohp = itemView.findViewById(R.id.no_hp)
//            Jkel = itemView.findViewById(R.id.jkel)
            Listitem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_desgin,
        parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama:String? = listdata_teman[position].nama
        val Alamat:String? = listdata_teman[position].alamat
        val Nohp:String? = listdata_teman[position].no_hp
//        val Jkel:String? = listdata_teman.get(position).jkel

        holder.Nama.text = "Nama $Nama"
        holder.Alamat.text = "Alamat $Alamat"
        holder.Nohp.text = "Telepon $Nohp"
//        holder.Nama.text = "Jenis Kelamin $Jkel"
        holder.Listitem.setOnLongClickListener { true }
    }

    override fun getItemCount(): Int {
        return listdata_teman.size
    }
    init {
        this.context = context
    }
}

