package com.example.proyectofinalmodulo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmodulo.AdapterAnimales.Animales
import com.example.proyectofinalmodulo.AdapterAnimales.AnimalesAdapter
import com.example.proyectofinalmodulo.AdapterUsuarios.Usuarios
import com.example.proyectofinalmodulo.AdapterUsuarios.UsuariosAdapter
import com.example.proyectofinalmodulo.databinding.ActivityListadoBinding
import com.example.proyectofinalmodulo.databinding.ActivityListadoUsuariosBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListadoUsuarios : AppCompatActivity() {
    private lateinit var listaUsuarios: ArrayList<Usuarios>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: UsuariosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListadoUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recycler.addItemDecoration(decoration)

        listaUsuarios = ArrayList()
        recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = UsuariosAdapter(listaUsuarios)
        recycler.adapter = adapter

        cargarDatos()
    }

    private fun cargarDatos() {
        // Obtiene una instancia de la base de datos Firestore y el email del usuario actual
        val db = FirebaseFirestore.getInstance()


        db.collection("usuarios")
            .get()
            .addOnSuccessListener { cargar ->
                cargar.forEach{ document ->
                    var usuario = document.toObject(Usuarios::class.java)
                    listaUsuarios.add(usuario)
                }
                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener {
                Log.e("Cargar", "Error en la obtención de animales")
            }

    }
}