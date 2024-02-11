package com.example.proyectofinalmodulo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter=AnimalesAdapter(AnimalesProvider.animalesList)
    }
}