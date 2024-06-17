package com.example.proyectofinalmodulo.AdapterUsuarios

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.databinding.ItemUsuarioBinding

class UsuariosViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemUsuarioBinding.bind(view)
    fun render(usuarioModel: Usuarios){
        binding.nombreUsuario.text = usuarioModel.Nombre
        binding.apellidosUsuario.text = usuarioModel.Apellidos
        binding.correoUsuario.text = usuarioModel.correo
        binding.telefono.text = usuarioModel.Telefono
        binding.rol.text = usuarioModel.Rol
    }
}