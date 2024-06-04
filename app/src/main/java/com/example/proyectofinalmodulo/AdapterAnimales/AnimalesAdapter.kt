package com.example.proyectofinalmodulo.AdapterAnimales


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.R

class AnimalesAdapter(private var animalesList:List<Animales>): RecyclerView.Adapter<AnimalesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalesViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return AnimalesViewHolder(layoutInflater.inflate(R.layout.item_animales,parent,false))
    }

    override fun getItemCount(): Int {
        return animalesList.size
    }

    override fun onBindViewHolder(holder: AnimalesViewHolder, position: Int) {
       val item = animalesList[position]
       holder.render(item)
    }

    fun actualizarAnimales(listaAnimales: List<Animales>){
        this.animalesList = listaAnimales
        notifyDataSetChanged()
    }
}