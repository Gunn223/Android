package com.example.temanku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.temanku.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:ActivityMainBinding

    private var auth:FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val logout = binding.logout
        val save = binding.save
        val lihatdata = binding.lihatdata

        logout.setOnClickListener(this)

        save.setOnClickListener(this)

        lihatdata.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }
    private fun isEmpty(s:String): Boolean{
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View){
        when (v.id){
            R.id.save -> {
                val getUserId = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNama:String = binding.nama.text.toString()
                val getAlamat:String = binding.alamat.text.toString()
                val getNoHP:String = binding.noHp.text.toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNama) || isEmpty(getAlamat) || isEmpty(getNoHP)){
                    Toast.makeText(this@MainActivity, "Data tidak Boleh ada yang kosong",Toast.LENGTH_SHORT).show()
                }else{
                    getReference.child("admin").child(getUserId).child("Data Teman").push()
                        .setValue(data_teman(getNama,getAlamat,getNoHP))
                        .addOnCompleteListener(this){
                            binding.nama.setText("")
                            binding.alamat.setText("")
                            binding.noHp.setText("")
                            Toast.makeText(this@MainActivity, "Data tersimpan",Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout ->{
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                        override fun onComplete(p0: Task<Void>){
                            Toast.makeText(this@MainActivity,"logout berhasil",Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
            R.id.lihatdata->{

            }
        }
    }

}

