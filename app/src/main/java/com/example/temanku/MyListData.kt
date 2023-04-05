package com.example.temanku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyListData:AppCompatActivity(), RecyclerViewAdapter.data_listener {

    private var recyclerview:RecyclerView? = null
    private var adapter:RecyclerView.Adapter<*>?=null
    private var layoutManager:RecyclerView.LayoutManager? = null

    val database = FirebaseDatabase.getInstance()
    private var dataTeman = ArrayList<data_teman>()
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mylistdata)
        recyclerview = findViewById(R.id.datalist)
        supportActionBar!!.title = "DataTeman"
        auth = FirebaseAuth.getInstance()
        myRecycleView()
        GetData()
    }


    private fun GetData() {
        Toast.makeText(applicationContext,"Mohon tunggu sebentar... ",
            Toast.LENGTH_LONG).show()
        val getUserId:String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("Admin").child(getUserId).child("DataTeman")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(dataSnapshot:DataSnapshot) {
                    if (dataSnapshot.exists()){
                        dataTeman.clear()
                        for (snapsot in dataSnapshot.children){
                            val teman = snapsot.getValue(data_teman::class.java)
                            teman?.key = snapsot.key
                            dataTeman.add(teman!!)
                        }
                        adapter = RecyclerViewAdapter(dataTeman,this@MyListData)
                        recyclerview?.adapter = adapter
                        (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                        Toast.makeText(applicationContext,"Data Berhasil Dimuat",
                        Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext,"Data Gagal Dimuat",
                    Toast.LENGTH_LONG).show()
                    Log.e("mylistdata", databaseError.details +""+
                    databaseError.message)
                }
            } )
    }

    private fun myRecycleView() {
        layoutManager = LinearLayoutManager(this)
        recyclerview?.layoutManager = layoutManager
        recyclerview?.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.line)!!)
        recyclerview?.addItemDecoration(itemDecoration)
    }

    override fun OnDeleteData(data: data_teman?, position: Int) {
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getrefrence = database.getReference()

        if (getrefrence != null){

            getrefrence.child("Admin").child(getUserID).child("DataTeman").child(data?.key.toString()).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this@MyListData,  "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext,MyListData::class.java)
                    startActivity(intent)
                    finish()
                }} else {
            Toast.makeText(this@MyListData, "Data Gagaldihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
