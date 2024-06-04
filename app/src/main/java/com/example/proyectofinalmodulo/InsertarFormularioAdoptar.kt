package com.example.proyectofinalmodulo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.databinding.ActivityInsertarFormularioAdoptarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InsertarFormularioAdoptar : AppCompatActivity() {
    private lateinit var binding: ActivityInsertarFormularioAdoptarBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarFormularioAdoptarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        infoCorreo()

        binding.bEnviarFormulario.setOnClickListener {
            if (isFormValid()) {
                saveFormData()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun infoCorreo() {
        val currentUser = auth.currentUser
        currentUser?.let {
            val email = it.email
            binding.tbCorreoAdoptante.setText(email)
        }
    }

    private fun isFormValid(): Boolean {
        return binding.tbNombreAdoptante.text.isNotEmpty() &&
                binding.tbApellidosAdoptante.text.isNotEmpty() &&
                binding.tbTelefonoAdoptante.text.isNotEmpty() &&
                binding.tbCorreoAdoptante.text.isNotEmpty() &&
                binding.tbViviendaAdoptante.text.isNotEmpty() &&
                binding.tbMotivoAdopcion.text.isNotEmpty() &&
                binding.tbExperienciaAdoptar.text.isNotEmpty() &&
                binding.tbMasAnimales.text.isNotEmpty()
    }

    private fun saveFormData() {
        val formData = mapOf(
            "Nombre" to binding.tbNombreAdoptante.text.toString(),
            "Apellidos" to binding.tbApellidosAdoptante.text.toString(),
            "Telefono" to binding.tbTelefonoAdoptante.text.toString(),
            "Correo" to binding.tbCorreoAdoptante.text.toString(),
            "TipoVivienda" to binding.tbViviendaAdoptante.text.toString(),
            "MotivoAdopcion" to binding.tbMotivoAdopcion.text.toString(),
            "ExperienciaAdoptar" to binding.tbExperienciaAdoptar.text.toString(),
            "MasAnimales" to binding.tbMasAnimales.text.toString()
        )

        db.collection("formularioAdoptar")
            .document(binding.tbCorreoAdoptante.text.toString())
            .set(formData)
            .addOnSuccessListener {
                sendEmailWithFormData()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar el formulario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun sendEmailWithFormData() {
        val formData = """
            Nombre: ${binding.tbNombreAdoptante.text}
            Apellidos: ${binding.tbApellidosAdoptante.text}
            Teléfono: ${binding.tbTelefonoAdoptante.text}
            Correo: ${binding.tbCorreoAdoptante.text}
            Tipo de Vivienda: ${binding.tbViviendaAdoptante.text}
            Motivo de Adopción: ${binding.tbMotivoAdopcion.text}
            Experiencia al Adoptar: ${binding.tbExperienciaAdoptar.text}
            Otros Animales: ${binding.tbMasAnimales.text}
        """.trimIndent()

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tbCorreoAdoptante.text.toString())) // Puede cambiarse al correo deseado
            putExtra(Intent.EXTRA_SUBJECT, "Formulario de Adopción")
            putExtra(Intent.EXTRA_TEXT, formData)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo con:"))
            finish() // Cierra la actividad después de intentar enviar el correo
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show()
        }
    }
}




