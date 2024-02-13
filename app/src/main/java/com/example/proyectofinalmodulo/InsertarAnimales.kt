package com.example.proyectofinalmodulo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalmodulo.databinding.ActivityInsertarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore

class InsertarAnimales : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.bGuardarAnimal.setOnClickListener {
            if( binding.tbNombreAnimal.text.isNotEmpty() && binding.tbRaza.text.isNotEmpty() &&
                binding.tbAlimentacion.text.isNotEmpty() && binding.tbAnioNacimiento.text.isNotEmpty() &&
                binding.tbDescripcion.text.isNotEmpty()){
                db.collection("animales").document(binding.tbNombreAnimal.text.toString())
                    .set(mapOf(
                        "Nombre" to binding.tbNombreAnimal.text.toString(),
                        "Raza" to binding.tbRaza.text.toString(),
                        "Alimentacion" to binding.tbAlimentacion.text.toString(),
                        "AnioNacimiento" to binding.tbAnioNacimiento.text.toString(),
                        "Descripcion" to binding.tbDescripcion.text.toString()

                    ))
                    .addOnSuccessListener {
                        // Operación exitosa, enviar a la nueva actividad
                        val intent = Intent(this, ListadoActivity::class.java)
                        startActivity(intent)

                        binding.tbNombreAnimal.text = null
                        binding.tbRaza.text = null
                        binding.tbAlimentacion.text = null
                        binding.tbAnioNacimiento.text = null
                        binding.tbDescripcion.text = null
                    }

            }else{ Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()}
        }
    }
}