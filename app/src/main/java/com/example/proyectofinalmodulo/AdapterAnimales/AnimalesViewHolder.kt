package com.example.proyectofinalmodulo.AdapterAnimales

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalmodulo.InsertarFormularioAdoptar
import com.example.proyectofinalmodulo.databinding.ItemAnimalesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AnimalesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemAnimalesBinding.bind(view)

    fun render(animalesModel: Animales) {
        binding.NombreAnimal.text = animalesModel.nombre
        binding.RazaAnimal.text = animalesModel.raza
        binding.AlimentacionAnimal.text = animalesModel.alimentacion
        binding.AnioAnimal.text = animalesModel.edadAnimal
        binding.numeroChip.text = animalesModel.numeroChip

        // Cargar la imagen usando Glide
        Glide.with(binding.imagenAnimal.context)
            .load(animalesModel.imagenUrl)
            .into(binding.imagenAnimal)

        // Obtener el email del usuario actual
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        if (currentUserEmail != null) {
            FirebaseFirestore.getInstance().collection("usuarios")
                .document(currentUserEmail)
                .get()
                .addOnSuccessListener { document ->
                    val rolUsuario = document.getString("Rol").toString()
                    // Ocultar el botón "Adoptar" si el usuario es un administrador
                    if (rolUsuario == "Admin") {
                        binding.btnAdoptar.visibility = View.GONE
                    } else {
                        binding.btnAdoptar.visibility = View.VISIBLE
                        binding.btnAdoptar.setOnClickListener {
                            val context = it.context
                            val intent = Intent(context, InsertarFormularioAdoptar::class.java)
                            context.startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Usuario", "Error al obtener el rol del usuario", exception)
                    // En caso de fallo, asumir que no es admin
                    binding.btnAdoptar.visibility = View.VISIBLE
                    binding.btnAdoptar.setOnClickListener {
                        val context = it.context
                        val intent = Intent(context, InsertarFormularioAdoptar::class.java)
                        context.startActivity(intent)
                    }
                }
        } else {
            // En caso de que no se pueda obtener el email, asumir que no es admin
            binding.btnAdoptar.visibility = View.VISIBLE
            binding.btnAdoptar.setOnClickListener {
                val context = it.context
                val intent = Intent(context, InsertarFormularioAdoptar::class.java)
                context.startActivity(intent)
            }
        }
    }
}


