package com.example.proyectofinalmodulo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectofinalmodulo.databinding.ActivityInsertarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class InsertarAnimales : AppCompatActivity() {

    private lateinit var binding: ActivityInsertarAnimalesBinding
    private var selectedImageUri: Uri? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarAnimalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        // Initialize the permission launcher
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize the gallery launcher
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    binding.animalImageView.setImageURI(selectedImageUri)
                    binding.animalImageView.visibility = View.VISIBLE
                }
            }
        }

        binding.bGuardarAnimal.setOnClickListener {
            if (binding.tbNombreAnimal.text.isNotEmpty() && binding.tbRaza.text.isNotEmpty() &&
                binding.tbAlimentacion.text.isNotEmpty() && binding.tbAnioNacimiento.text.isNotEmpty() &&
                binding.tbDescripcion.text.isNotEmpty() && binding.tbFechaIngreso.text.isNotEmpty()
            ) {
                val animalData = mapOf(
                    "nombre" to binding.tbNombreAnimal.text.toString(),
                    "raza" to binding.tbRaza.text.toString(),
                    "alimentacion" to binding.tbAlimentacion.text.toString(),
                    "anioNacimiento" to binding.tbAnioNacimiento.text.toString(),
                    "descripcion" to binding.tbDescripcion.text.toString(),
                    "fechaIngreso" to binding.tbFechaIngreso.text.toString()
                )

                if (selectedImageUri != null) {
                    val storageReference = FirebaseStorage.getInstance().reference
                    val imageReference = storageReference.child("images/${UUID.randomUUID()}.jpg")
                    imageReference.putFile(selectedImageUri!!)
                        .addOnSuccessListener {
                            imageReference.downloadUrl.addOnSuccessListener { uri ->
                                val imageUrl = uri.toString()
                                val animalDataWithImage = animalData + ("imageUrl" to imageUrl)
                                db.collection("animales").document(binding.tbNombreAnimal.text.toString())
                                    .set(animalDataWithImage)
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
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                        }
                } else {
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
                }

            } else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bAbrirGaleria.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                } else {
                    openGallery()
                }
            } else {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }
}
