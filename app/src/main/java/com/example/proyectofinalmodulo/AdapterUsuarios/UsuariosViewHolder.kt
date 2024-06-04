package com.example.proyectofinalmodulo.AdapterUsuarios

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalmodulo.databinding.ItemAnimalesBinding
import com.example.proyectofinalmodulo.databinding.ItemUsuariosBinding

class UsuariosViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemUsuariosBinding.bind(view)

    fun render(usuariosModel: Usuarios) {
        binding.NombreAnimal.text = usuariosModel.nombre
        binding.ApellidosUsuario.text = usuariosModel.apellidos
        binding.CorreoUsuario.text = usuariosModel.correo
        binding.RolUsuario.text = usuariosModel.rol
        binding.TelefonoUsuario.text = usuariosModel.telefono
    }
}

