package com.example.proyectofinalmodulo.AccionesAnimales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalmodulo.ListadoActivity
import com.example.proyectofinalmodulo.databinding.ActivityActualizarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarAnimales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityActualizarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.bActualizar.setOnClickListener{
            val nombre = binding.tbNombreActualizar.text.toString()
            val alimentacion = binding.tbAlimentacionActualizar.text.toString()
            val descripcion = binding.tbDescripcionActualizar.text.toString()

            val animalData = hashMapOf(
                "alimentacion" to alimentacion,
                "descripcion" to descripcion
            )

            db.collection("animales")
                .document(nombre)
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
