package com.example.proyectofinalmodulo.AccionesAnimales

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalmodulo.ListadoActivity
import com.example.proyectofinalmodulo.databinding.ActivityEliminarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore

class EliminarAnimales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEliminarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("EliminarAnimal")
        val db = FirebaseFirestore.getInstance()

        binding.bEliminar.setOnClickListener{
            // Crear el AlertDialog para confirmar la eliminación
            AlertDialog.Builder(this)
                .setTitle("Confirmar Eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar este animal?")
                .setPositiveButton("Sí") { dialog, which ->
                    // Proceder con la eliminación si se confirma
                    db.collection("animales")
                        .document(binding.tbChipEliminar.text.toString())
                        .delete()
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
                .setNegativeButton("No", null) // No hacer nada si se cancela
                .show()
        }
    }
}


