package com.example.proyectofinalmodulo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalmodulo.databinding.ActivityEliminarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore

class EliminarAnimales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEliminarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.bEliminar.setOnClickListener{
            db.collection("animales")
                .document(binding.tbNombreEliminar.text.toString())
                .delete()

            val intent = Intent(this, ListadoActivity::class.java)
            startActivity(intent)
        }
    }
}