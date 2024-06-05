package com.example.proyectofinalmodulo.Validaciones

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ChipValidator(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()

    fun validateChipNumber(chipNumber: String, onValidationResult: (Boolean) -> Unit) {
        if (!chipNumber.matches(Regex("^CHP\\d{3}$"))) {
            Toast.makeText(context, "El número de chip debe tener el formato CHP000", Toast.LENGTH_SHORT).show()
            onValidationResult(false)
            return
        }

        db.collection("animales").whereEqualTo("numeroChip", chipNumber).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onValidationResult(true)
                } else {
                    Toast.makeText(context, "El número de chip ya existe", Toast.LENGTH_SHORT).show()
                    onValidationResult(false)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al validar el número de chip", Toast.LENGTH_SHORT).show()
                onValidationResult(false)
            }
    }
}
