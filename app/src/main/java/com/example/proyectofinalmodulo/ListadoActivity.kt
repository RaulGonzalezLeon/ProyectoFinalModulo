package com.example.proyectofinalmodulo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.AdapterAnimales.Animales
import com.example.proyectofinalmodulo.AdapterAnimales.AnimalesAdapter
import com.example.proyectofinalmodulo.databinding.ActivityListadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListadoActivity : ActivityWithMenus() {
    private lateinit var listaAnimales: ArrayList<Animales>
    private lateinit var listaFiltrada: ArrayList<Animales>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AnimalesAdapter
    private lateinit var editTextRaza: EditText
    private lateinit var buttonFiltrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("ListadoAnimales")

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recycler.addItemDecoration(decoration)

        listaAnimales = ArrayList()
        listaFiltrada = ArrayList()
        recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = AnimalesAdapter(listaFiltrada)
        recycler.adapter = adapter

        editTextRaza = binding.editTextRaza
        buttonFiltrar = binding.buttonFiltrar

        buttonFiltrar.setOnClickListener {
            actualizarListaFiltrada()
        }

        cargarDatos()
    }

    private fun cargarDatos() {
        // Obtiene una instancia de la base de datos Firestore y el email del usuario actual
        val db = FirebaseFirestore.getInstance()

        db.collection("animales")
            .get()
            .addOnSuccessListener { cargar ->
                listaAnimales.clear()
                cargar.forEach { document ->
                    val animal = document.toObject(Animales::class.java)
                    listaAnimales.add(animal)
                }
                actualizarListaFiltrada()
            }
            .addOnFailureListener {
                Log.e("Cargar", "Error en la obtención de animales")
            }
    }

    private fun actualizarListaFiltrada() {
        val razaIntroducida = editTextRaza.text.toString().trim()
        listaFiltrada.clear()

        if (razaIntroducida.isEmpty()) {
            listaFiltrada.addAll(listaAnimales)
        } else {
            listaFiltrada.addAll(listaAnimales.filter { it.raza.equals(razaIntroducida, ignoreCase = true) })
        }

        adapter.notifyDataSetChanged()
    }
}

