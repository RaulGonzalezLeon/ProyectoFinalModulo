package com.example.proyectofinalmodulo.AdapterUsuarios

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalmodulo.databinding.ItemAnimalesBinding

class UsuariosViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemAnimalesBinding.bind(view)

    fun render(usuariosModel: Usuarios) {
        binding.NombreAnimal.text = usuariosModel.nombre
        binding.RazaAnimal.text = usuariosModel.raza
        binding.AlimentacionAnimal.text = usuariosModel.alimentacion
        binding.AnioAnimal.text = usuariosModel.anioNacimiento
        binding.DescripcionAnimal.text = usuariosModel.descripcion
        binding.AnioIngreso.text = usuariosModel.fechaIngreso

        // Cargar la imagen usando Glide
        Glide.with(binding.imagenAnimal.context)
            .load(usuariosModel.imagenUrl)
            .into(binding.imagenAnimal)
    }
}

