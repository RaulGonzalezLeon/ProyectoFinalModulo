package com.example.proyectofinalmodulo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.databinding.ActivityInsertarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class InsertarAnimales : AppCompatActivity() {

    private lateinit var binding: ActivityInsertarAnimalesBinding
    private lateinit var imagen: ImageButton
    private var imageUri: Uri? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            imagen.setImageURI(uri)
            imageUri = uri
        } else {
            Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        imagen = binding.imageButton

        imagen.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.bGuardarAnimal.setOnClickListener {
            if (binding.tbNombreAnimal.text.isNotEmpty() && binding.tbRaza.text.isNotEmpty() &&
                binding.tbAlimentacion.text.isNotEmpty() && binding.tbAnioNacimiento.text.isNotEmpty() &&
                binding.tbDescripcion.text.isNotEmpty() && binding.tbFechaIngreso.text.isNotEmpty()
            ) {
                if (imageUri != null) {
                    val fileName = UUID.randomUUID().toString() + ".jpg"
                    val imageRef = storageRef.child("animales/$fileName")

                    imageRef.putFile(imageUri!!)
                        .addOnSuccessListener { taskSnapshot ->
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                val animalData = mapOf(
                                    "nombre" to binding.tbNombreAnimal.text.toString(),
                                    "raza" to binding.tbRaza.text.toString(),
                                    "alimentacion" to binding.tbAlimentacion.text.toString(),
                                    "anioNacimiento" to binding.tbAnioNacimiento.text.toString(),
                                    "descripcion" to binding.tbDescripcion.text.toString(),
                                    "fechaIngreso" to binding.tbFechaIngreso.text.toString(),
                                    "imagenUrl" to uri.toString() // Añadir la URL de la imagen
                                )

                                db.collection("animales").document(binding.tbNombreAnimal.text.toString())
                                    .set(animalData)
                                    .addOnSuccessListener {
                                        val intent = Intent(this, ListadoActivity::class.java)
                                        startActivity(intent)

                                        binding.tbNombreAnimal.text = null
                                        binding.tbRaza.text = null
                                        binding.tbAlimentacion.text = null
                                        binding.tbAnioNacimiento.text = null
                                        binding.tbDescripcion.text = null
                                        binding.tbFechaIngreso.text = null
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Por favor seleccione una imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




