package com.example.proyectofinalmodulo.Validaciones

object ValidacionesUsuario {

    fun esCorreoValido(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun esContrasenaValida(contrasena: String): Boolean {
        return contrasena.length >= 6 // Ejemplo de regla: la contraseña debe tener al menos 6 caracteres
    }

    fun esNombreValido(nombre: String): Boolean {
        return nombre.isNotEmpty() && nombre.length <= 30
    }

    fun esApellidosValido(apellidos: String): Boolean {
        return apellidos.isNotEmpty() && apellidos.length <= 40
    }

    fun esRolValido(rol: String): Boolean {
        return rol.isNotEmpty()
    }

    fun esTelefonoValido(telefono: String): Boolean {
        return telefono.length == 9 && android.util.Patterns.PHONE.matcher(telefono).matches()
    }
}
