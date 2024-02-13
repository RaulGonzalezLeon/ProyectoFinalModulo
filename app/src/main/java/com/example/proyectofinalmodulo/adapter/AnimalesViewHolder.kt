package com.example.proyectofinalmodulo.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalmodulo.Animales
import com.example.proyectofinalmodulo.databinding.ItemAnimalesBinding

class AnimalesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemAnimalesBinding.bind(view)
    fun render(animalesModel: Animales){
        binding.NombreAnimal.text = animalesModel.nombre
        binding.RazaAnimal.text = animalesModel.raza
        binding.AlimentacionAnimal.text = animalesModel.alimentacion
        binding.AnioAnimal.text = animalesModel.anioNacimiento
        binding.DescripcionAnimal.text = animalesModel.descripcion
    }
}