package com.example.proyectofinalmodulo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalmodulo.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "RegistroUsuario"

        val db = FirebaseFirestore.getInstance()
        binding.bRegistrar.setOnClickListener {
            if(binding.tbCorreo.text.isNotEmpty() && binding.tbContrasena.text.isNotEmpty()
                && binding.tbNombre.text.isNotEmpty() && binding.tbApellidos.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.tbCorreo.text.toString(),binding.tbContrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful){

                        db.collection("usuarios").document(binding.tbCorreo.text.toString())
                            .set(mapOf(
                                "Nombre" to binding.tbNombre.text.toString(),
                                "Apellidos" to binding.tbApellidos.text.toString(),
                                "Telefono" to binding.tbTelefono.text.toString(),
                            ))



                        val intent = Intent(this,ListadoActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this, "Error en el registro del nuevo usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }else {Toast.makeText(this, "Algun campo vacio", Toast.LENGTH_SHORT).show()}
        }
    }
}