package com.example.proyectofinalmodulo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalmodulo.Validaciones.ValidacionesUsuario
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
            val correo = binding.tbCorreo.text.toString()
            val contrasena = binding.tbContrasena.text.toString()
            val nombre = binding.tbNombre.text.toString()
            val apellidos = binding.tbApellidos.text.toString()
            val rol = binding.tbRolUsuario.text.toString()
            val telefono = binding.tbTelefono.text.toString()

            if (ValidacionesUsuario.esCorreoValido(correo) && ValidacionesUsuario.esContrasenaValida(contrasena)
                && ValidacionesUsuario.esNombreValido(nombre) && ValidacionesUsuario.esApellidosValido(apellidos)
                && ValidacionesUsuario.esRolValido(rol) && ValidacionesUsuario.esTelefonoValido(telefono)) {

                ValidacionesUsuario.existeCorreo(correo) { existe ->
                    if (!existe) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener {
                            if (it.isSuccessful) {
                                db.collection("usuarios").document(correo)
                                    .set(mapOf(
                                        "Nombre" to nombre,
                                        "Apellidos" to apellidos,
                                        "Telefono" to telefono,
                                        "Rol" to rol,
                                        "correo" to correo
                                    ))

                                val intent = Intent(this, ListadoActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Error en el registro del nuevo usuario", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Algun campo vacio o invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


