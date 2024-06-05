package com.example.proyectofinalmodulo

import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.AdapterAnimales.AnimalesProvider.Companion.animalesList
import com.example.proyectofinalmodulo.AdapterAnimales.Animales
import com.example.proyectofinalmodulo.AdapterAnimales.AnimalesAdapter
import com.example.proyectofinalmodulo.databinding.ActivityListadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListadoActivity : ActivityWithMenus() {
    private lateinit var listaAnimales: ArrayList<Animales>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AnimalesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recycler.addItemDecoration(decoration)

        binding.filtro.addTextChangedListener { filtro ->
            val filtroAnimales = animalesList.filter { animales ->
                animales.nombre.lowercase().contains(filtro.toString().lowercase())}
            adapter.actualizarAnimales(filtroAnimales)
        }
        
        listaAnimales = ArrayList()
        recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = AnimalesAdapter(listaAnimales)
        recycler.adapter = adapter

        cargarDatos()



    }


    private fun cargarDatos() {
        // Obtiene una instancia de la base de datos Firestore y el email del usuario actual
        val db = FirebaseFirestore.getInstance()


        db.collection("animales")
            .get()
            .addOnSuccessListener { cargar ->
                cargar.forEach{ document ->
                    var animal = document.toObject(Animales::class.java)
                    listaAnimales.add(animal)
                }
                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener {
                Log.e("Cargar", "Error en la obtención de animales")
            }

    }
}