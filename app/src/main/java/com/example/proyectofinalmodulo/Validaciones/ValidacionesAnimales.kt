package com.example.proyectofinalmodulo.Validaciones

import android.content.Context
import android.widget.Toast

class ValidacionesAnimales(private val context: Context) {

    fun validateInputs(
        nombre: String,
        raza: String,
        alimentacion: String,
        anioNacimiento: String,
        callback: (Boolean) -> Unit
    ) {
        if (!validateNombre(nombre)) {
            showToast("El nombre no debe superar los 20 caracteres y no debe contener números")
            callback(false)
            return
        }

        if (!validateRaza(raza)) {
            showToast("La raza no debe superar los 30 caracteres y no debe contener números")
            callback(false)
            return
        }

        if (!validateAlimentacion(alimentacion)) {
            showToast("La alimentación no debe superar los 40 caracteres")
            callback(false)
            return
        }

        if (!validateAnioNacimiento(anioNacimiento)) {
            showToast("El año de nacimiento solo debe contener números")
            callback(false)
            return
        }

        callback(true)
    }

    private fun validateNombre(nombre: String): Boolean {
        return nombre.length <= 20 && nombre.all { it.isLetter() || it.isWhitespace() }
    }

    private fun validateRaza(raza: String): Boolean {
        return raza.length <= 30 && raza.all { it.isLetter() || it.isWhitespace() }
    }

    private fun validateAlimentacion(alimentacion: String): Boolean {
        return alimentacion.length <= 40
    }

    private fun validateAnioNacimiento(anioNacimiento: String): Boolean {
        return anioNacimiento.all { it.isDigit() }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
