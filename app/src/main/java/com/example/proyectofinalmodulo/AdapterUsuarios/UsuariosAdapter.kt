package com.example.proyectofinalmodulo.AdapterUsuarios


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.R

class UsuariosAdapter(private var usuariosList:List<Usuarios>): RecyclerView.Adapter<UsuariosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return UsuariosViewHolder(layoutInflater.inflate(R.layout.item_usuarios,parent,false))
    }

    override fun getItemCount(): Int {
        return usuariosList.size
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
       val item = usuariosList[position]
       holder.render(item)
    }
}