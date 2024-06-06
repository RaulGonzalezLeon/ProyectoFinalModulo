package com.example.proyectofinalmodulo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    public lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "LoginUsuario"


        binding.bAcceder.setOnClickListener {
            login()
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
}