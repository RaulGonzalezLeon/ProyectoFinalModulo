package com.example.proyectofinalmodulo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.databinding.ActivityInsertarFormularioAdoptarBinding
import com.google.firebase.firestore.FirebaseFirestore

class InsertarFormularioAdoptar : ActivityWithMenus() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInsertarFormularioAdoptarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.bEnviarFormulario.setOnClickListener {
            if(binding.tbNombreAdoptante.text.isNotEmpty() && binding.tbApellidosAdoptante.text.isNotEmpty() &&
                binding.tbTelefonoAdoptante.text.isNotEmpty() && binding.tbCorreoAdoptante.text.isNotEmpty() &&
                binding.tbViviendaAdoptante.text.isNotEmpty() && binding.tbMotivoAdopcion.text.isNotEmpty() &&
                binding.tbExperienciaAdoptar.text.isNotEmpty() && binding.tbMasAnimales.text.isNotEmpty() ){

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

                db.collection("formularioAdoptar").document(binding.tbNombreAdoptante.text.toString())
                    .set(formData)
                    .addOnSuccessListener {
                        // Enviar correo
                        sendEmail(formData)

                        // Operación exitosa, enviar a la nueva actividad
                        val intent = Intent(this, ListadoActivity::class.java)
                        startActivity(intent)
                    }
            }
        }
    }

    private fun sendEmail(formData: Map<String, String>) {
        val subject = "Nuevo Formulario de Adopción"
        val body = StringBuilder().apply {
            append("Se ha recibido un nuevo formulario de adopción con los siguientes datos:\n\n")
            formData.forEach { (key, value) ->
                append("$key: $value\n")
            }
        }.toString()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("raulgonzalezleon1@gmail.com")) // Reemplaza con el correo al que deseas enviar la información
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar correo..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            // Manejar el error en caso de que no haya clientes de correo instalados
        }
    }
}