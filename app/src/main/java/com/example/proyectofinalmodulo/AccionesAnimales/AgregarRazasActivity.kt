package com.example.proyectofinalmodulo.AccionesAnimales

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.databinding.ActivityAgregarRazasBinding
import com.google.firebase.firestore.FirebaseFirestore

class AgregarRazasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarRazasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarRazasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Agregar Razas"

        val db = FirebaseFirestore.getInstance()

        binding.bAgregarRaza.setOnClickListener {
            val raza = binding.etRaza.text.toString()
            if (raza.isNotEmpty()) {
                val razaData = mapOf("nombre" to raza)
                db.collection("razas").add(razaData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Raza añadida correctamente", Toast.LENGTH_SHORT).show()
                        binding.etRaza.text.clear()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al añadir la raza", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "El campo de raza está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
