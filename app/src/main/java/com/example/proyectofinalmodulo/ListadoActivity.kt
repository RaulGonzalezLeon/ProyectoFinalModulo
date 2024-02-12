package com.example.proyectofinalmodulo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalmodulo.adapter.AnimalesAdapter
import com.example.proyectofinalmodulo.databinding.ActivityListadoBinding
import com.example.proyectofinalmodulo.databinding.ActivityRegistroBinding

class ListadoActivity : AppCompatActivity() {
    lateinit var binding: ActivityListadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        var adapter = AnimalesAdapter(AnimalesProvider.animalesList)
        val manager = LinearLayoutManager(this)

        binding.recycler.layoutManager = manager
        binding.recycler.adapter=AnimalesAdapter(AnimalesProvider.animalesList)
        binding.recycler.addItemDecoration(decoration)

        binding.filtro.addTextChangedListener { filtro ->
            val filtroAnimales = AnimalesProvider.animalesList.filter { animales ->
                animales.nombre.lowercase().contains(filtro.toString().lowercase())}
            adapter.actualizarAnimales(filtroAnimales)
        }
    }
}