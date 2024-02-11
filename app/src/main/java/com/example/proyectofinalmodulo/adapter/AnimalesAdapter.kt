package com.example.proyectofinalmodulo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.AnimalesProvider.Companion.animalesList

class AnimalesAdapter: RecyclerView.Adapter<AnimalesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalesViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return animalesList.size
    }

    override fun onBindViewHolder(holder: AnimalesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}