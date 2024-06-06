package com.example.proyectofinalmodulo.Validaciones

import com.google.firebase.firestore.FirebaseFirestore

object ValidacionesUsuario {

    fun esCorreoValido(correo: String): Boolean {
        return correo.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun esContrasenaValida(contrasena: String): Boolean {
        return contrasena.isNotEmpty() && contrasena.length >= 6
    }

    fun esNombreValido(nombre: String): Boolean {
        return nombre.isNotEmpty()
    }

    fun esApellidosValido(apellidos: String): Boolean {
        return apellidos.isNotEmpty()
    }

    fun esRolValido(rol: String): Boolean {
        return rol.isNotEmpty()
    }

    fun esTelefonoValido(telefono: String): Boolean {
        return telefono.isNotEmpty() && android.util.Patterns.PHONE.matcher(telefono).matches()
    }

    fun existeCorreo(correo: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(correo).get().addOnSuccessListener { document ->
            callback(document.exists())
        }.addOnFailureListener {
            callback(false)
        }
    }
}

