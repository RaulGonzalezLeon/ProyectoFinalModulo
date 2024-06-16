package com.example.proyectofinalmodulo.AdapterUsuarios

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.databinding.ItemUsuarioBinding

class UsuariosViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemUsuarioBinding.bind(view)
    fun render(usuariosModel: Usuarios){
        binding.nombreUsuario.text = usuariosModel.nombre
        binding.apellidosUsuario.text = usuariosModel.apellidos
        binding.correoUsuario.text = usuariosModel.correo
        binding.telefono.text = usuariosModel.telefono
        binding.rol.text = usuariosModel.rol
    }
}