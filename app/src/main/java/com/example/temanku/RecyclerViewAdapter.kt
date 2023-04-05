package com.example.temanku

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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

    var listener: data_listener? = null

    interface data_listener {
        fun OnDeleteData(data:data_teman?, position: Int)
    }


    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama:String? = listdata_teman[position].nama
        val Alamat:String? = listdata_teman[position].alamat
        val Nohp:String? = listdata_teman[position].no_hp
//       val Jkel:String? = listdata_teman.get(position).jkel

        holder.Nama.text = "Nama $Nama"
        holder.Alamat.text = "Alamat $Alamat"
        holder.Nohp.text = "Telepon $Nohp"
//        holder.Nama.text = "Jenis Kelamin $Jkel"
        holder.Listitem.setOnLongClickListener(object :View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.Listitem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener{ dialog, i ->
                        when (i) { 0 -> {
                                val bundle = Bundle()
                                bundle.putString("dataNama", listdata_teman[position].nama)
                                bundle.putString("dataAlamat", listdata_teman[position].alamat)
                                bundle.putString("dataNomorHp", listdata_teman[position].no_hp)
                                bundle.putString("getPrimaryKey", listdata_teman[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {
                                listener?.OnDeleteData(listdata_teman.get(position),position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listdata_teman.size
    }
    init {
        this.context = context
        this.listener= context as MyListData
    }
}

