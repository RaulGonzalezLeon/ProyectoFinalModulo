package com.example.proyectofinalmodulo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.proyectofinalmodulo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ActivityWithMenus() {
    public lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "LoginUsuario"

        binding.bAcceder.setOnClickListener {
            login()
            obtenerPermisos()
        }
        binding.bRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun login() {
        if(binding.tbCorreoAcceso.text.isNotEmpty() && binding.tbContrasenaAcceso.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.tbCorreoAcceso.text.toString(),
                binding.tbContrasenaAcceso.text.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this,ListadoActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this, "Correo o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {Toast.makeText(this, "Algun campo esta Vacio", Toast.LENGTH_SHORT).show()}
    }

    private fun obtenerPermisos () {
        val auth = FirebaseAuth.getInstance()
        val correo = auth.currentUser?.email.toString()
        Log.d("Correo", correo)
        var db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(correo).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    Log.d("Correo", correo)
                    val privilegios = documentSnapshot.getString("Rol")
                    Log.d("Privilegio", privilegios.toString())

                    val intent = Intent(this, ActivityWithMenus::class.java)
                    intent.putExtra("privilegios", privilegios)

                    /*if (privilegios == "Usuario") {
                       binding.navView.menu.findItem(R.id.agregar_animal).isVisible =
                            false
                        binding.navView.menu.findItem(R.id.eliminar_animal).isVisible = false

                    }
                    if (privilegios == "Admin") {
                        //binding.navView.menu.findItem(R.id.agregar_animal).isVisible =
                         //   true
                       // binding.navView.menu.findItem(R.id.eliminar_animal).isVisible
                        //= true
                    }*/
                }
                Log.d("Usuario", "Datos Usuario: ${documentSnapshot.data}")
            }
    }




}