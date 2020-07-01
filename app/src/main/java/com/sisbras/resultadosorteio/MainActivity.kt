package com.sisbras.resultadosorteio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this){}

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        listaResultados()

        btnDesenv.setOnClickListener {
            val number = +5521972464530
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

    private fun listaResultados() {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("sorteio")

        val listaResultado: ArrayList<Resultado> = arrayListOf()
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaResultado.clear()
                for (data in dataSnapshot.children) {

                    val vendaData = data.getValue<Resultado>(Resultado::class.java)
                    //unwrap
                    val venda = vendaData?.let { it } ?: continue
                    venda.let { listaResultado.add(it) }
                }

                montaRecycle(listaResultado)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("Erro", databaseError.toString())
            }
        }
        mDatabaseReference?.addValueEventListener(postListener)

    }

    private fun montaRecycle(listaResultado: ArrayList<Resultado>) {
        rvResultados.clearOnChildAttachStateChangeListeners()
        val linearLayoutManager = LinearLayoutManager(this)
        rvResultados.layoutManager = linearLayoutManager
        rvResultados.adapter = ResultadoAdapter(listaResultado, this::act)
        //scroll to bottom
        rvResultados.scrollToPosition(listaResultado.size)

    }

    private fun act (data : Resultado) : Unit {
        var txtPin: String? = null
    }
}