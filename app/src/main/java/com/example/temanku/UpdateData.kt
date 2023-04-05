package com.example.temanku

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.temanku.databinding.ActivityLoginBinding
import com.example.temanku.databinding.ActivityUpdateDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateData:AppCompatActivity() {
    private var database:DatabaseReference?= null
    private var auth:FirebaseAuth?= null
    private var cekNama:String? = null
    private var cekAlamat:String? = null
    private var cekNomorhp:String? = null
    private lateinit var binding: ActivityUpdateDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar!!.title ="Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        binding.Update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = binding.newNama.getText().toString()
                cekAlamat = binding.newNama.getText().toString()
                cekNomorhp = binding.newNama.getText().toString()

                if (isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekNomorhp!!) ){
                    Toast.makeText(this@UpdateData,
                    "Data Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT).show()
                }else{
                    val setdata_teman = data_teman()
                    setdata_teman.nama = binding.newNama.getText().toString()
                    setdata_teman.alamat = binding.newAlamat.getText().toString()
                    setdata_teman.no_hp = binding.newNohp.getText().toString()
                    updateTeman(setdata_teman)
                }
            }
            
        })

    }

    private fun isEmpty(cekNama: String): Boolean {
        return TextUtils.isEmpty(cekNama)
    }
    private val data:Unit
    private get() {
        val getnama = intent.extras!!.getString("dataNama")
        val getAlamat = intent.extras!!.getString("dataAlamat")
        val getNoHP = intent.extras!!.getString("dataNomorHp")

        binding.newNama!!.setText(getnama)
        binding.newAlamat!!.setText(getAlamat)
        binding.newNohp!!.setText(getNoHP)
    }
    private fun updateTeman(teman:data_teman){
        val userID = auth!!.uid
        val getkey =intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("DataTeman")
            .child(getkey!!)
            .setValue(teman)
            .addOnSuccessListener {
                binding.newNama!!.setText("")
                binding.newAlamat!!.setText("")
                binding.newNohp!!.setText("")

                Toast.makeText(this@UpdateData,"Data Berhasil Diubah",
                Toast.LENGTH_SHORT).show()
                finish()
            }
    }


}