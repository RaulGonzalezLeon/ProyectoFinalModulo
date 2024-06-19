package com.example.proyectofinalmodulo.AccionesAnimales

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.ListadoActivity
import com.example.proyectofinalmodulo.R
import com.example.proyectofinalmodulo.databinding.ActivityActualizarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarAnimales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityActualizarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("ActualizarAnimal")

        val db = FirebaseFirestore.getInstance()

        val spinnerChip = findViewById<Spinner>(R.id.spinnerChipActualizar)
        val spinnerAlimentacion = findViewById<Spinner>(R.id.spinnerAlimentacionActualizar)

        // Poblar spinnerChip con números de chip
        db.collection("animales").get().addOnSuccessListener { documents ->
            val chipNumbers = ArrayList<String>()
            for (document in documents) {
                document.getString("numeroChip")?.let { chipNumbers.add(it) }
            }
            val adapterChip = ArrayAdapter(this, android.R.layout.simple_spinner_item, chipNumbers)
            adapterChip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerChip.adapter = adapterChip
        }

        // Configurar el spinnerAlimentacion con opciones estáticas
        val alimentacionOptions = listOf("Semi-Humeda", "Cocida Casera", "Hipoalergénicas", "Medicadas")
        val adapterAlimentacion = ArrayAdapter(this, android.R.layout.simple_spinner_item, alimentacionOptions)
        adapterAlimentacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAlimentacion.adapter = adapterAlimentacion

        binding.bActualizar.setOnClickListener {
            val chipNumber = spinnerChip.selectedItem.toString()
            val alimentacion = spinnerAlimentacion.selectedItem.toString()

            val animalData = hashMapOf(
                "alimentacion" to alimentacion,
            )

            db.collection("animales")
                .document(chipNumber)
                .update(animalData as Map<String, Any>)
                .addOnSuccessListener {
                    val intent = Intent(this, ListadoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish() // Cierra la activity actual
                }
                .addOnFailureListener { e ->
                    // Manejo de errores
                    e.printStackTrace()
                }
        }
    }
}
