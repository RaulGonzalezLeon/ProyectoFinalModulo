package com.example.proyectofinalmodulo.AdapterAnimales

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalmodulo.InsertarFormularioAdoptar
import com.example.proyectofinalmodulo.databinding.ItemAnimalesBinding

class AnimalesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemAnimalesBinding.bind(view)

    fun render(animalesModel: Animales) {
        binding.NombreAnimal.text = animalesModel.nombre
        binding.RazaAnimal.text = animalesModel.raza
        binding.AlimentacionAnimal.text = animalesModel.alimentacion
        binding.AnioAnimal.text = animalesModel.anioNacimiento
        binding.numeroChip.text = animalesModel.numeroChip

        // Cargar la imagen usando Glide
        Glide.with(binding.imagenAnimal.context)
            .load(animalesModel.imagenUrl)
            .into(binding.imagenAnimal)

        binding.btnAdoptar.setOnClickListener {
            val context = it.context
            val intent = Intent(context, InsertarFormularioAdoptar::class.java)
            context.startActivity(intent)
        }
    }
}

