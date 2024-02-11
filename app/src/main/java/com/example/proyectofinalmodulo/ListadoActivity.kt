package com.example.proyectofinalmodulo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalmodulo.databinding.ActivityListadoBinding
import com.example.proyectofinalmodulo.databinding.ActivityRegistroBinding

class ListadoActivity : AppCompatActivity() {
    lateinit var binding: ActivityListadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}