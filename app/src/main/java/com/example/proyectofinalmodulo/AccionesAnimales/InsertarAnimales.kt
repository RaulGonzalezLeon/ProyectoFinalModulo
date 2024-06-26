package com.example.proyectofinalmodulo.AccionesAnimales

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.ListadoActivity
import com.example.proyectofinalmodulo.Validaciones.ChipValidator
import com.example.proyectofinalmodulo.Validaciones.ValidacionesAnimales
import com.example.proyectofinalmodulo.databinding.ActivityInsertarAnimalesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class InsertarAnimales : AppCompatActivity() {

    private lateinit var binding: ActivityInsertarAnimalesBinding
    private lateinit var imagen: ImageButton
    private lateinit var spinnerRaza: Spinner
    private lateinit var spinnerAlimentacion: Spinner
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

        setTitle("Insertar Animales")

        imagen = binding.imageButton
        spinnerRaza = binding.spinnerRaza
        spinnerAlimentacion = binding.spinnerAlimentacion

        // Configurar los Spinners
        setupSpinners()

        imagen.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.bGuardarAnimal.setOnClickListener {
            if (binding.tbNombreAnimal.text.isNotEmpty() &&
                binding.tbEdadAnimal.text.isNotEmpty() &&
                binding.tbNumeroChip.text.isNotEmpty()
            ) {
                showConfirmationDialog()
            } else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinners() {
        // Configurar el spinner de alimentación
        val alimentaciones = listOf("Semi-Humeda", "Cocida Casera", "Hipoalergénicas", "Medicadas") // Añade más opciones según sea necesario
        val alimentacionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, alimentaciones)
        alimentacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAlimentacion.adapter = alimentacionAdapter

        // Cargar las razas desde Firestore
        loadRazasFromFirestore()
    }

    private fun loadRazasFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("razas").get()
            .addOnSuccessListener { result ->
                val razas = result.map { it.getString("nombre") ?: "" }
                val razaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, razas)
                razaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerRaza.adapter = razaAdapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar las razas", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Estás seguro de que quieres añadir este animal?")
            .setPositiveButton("Sí") { dialog, which ->
                addAnimal()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun addAnimal() {
        val db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        val chipValidator = ChipValidator(this)
        val inputValidator = ValidacionesAnimales(this)

        inputValidator.validateInputs(
            binding.tbNombreAnimal.text.toString(),
            spinnerRaza.selectedItem.toString(),
            spinnerAlimentacion.selectedItem.toString(),
            binding.tbEdadAnimal.text.toString()
        ) { areInputsValid ->
            if (areInputsValid) {
                chipValidator.validateChipNumber(binding.tbNumeroChip.text.toString()) { isValid ->
                    if (isValid) {
                        if (imageUri != null) {
                            val fileName = UUID.randomUUID().toString() + ".jpg"
                            val imageRef = storageRef.child("animales/$fileName")

                            imageRef.putFile(imageUri!!)
                                .addOnSuccessListener { taskSnapshot ->
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        val animalData = mapOf(
                                            "nombre" to binding.tbNombreAnimal.text.toString(),
                                            "raza" to spinnerRaza.selectedItem.toString(),
                                            "alimentacion" to spinnerAlimentacion.selectedItem.toString(),
                                            "edadAnimal" to binding.tbEdadAnimal.text.toString(),
                                            "numeroChip" to binding.tbNumeroChip.text.toString(),
                                            "imagenUrl" to uri.toString() // Añadir la URL de la imagen
                                        )

                                        db.collection("animales").document(binding.tbNumeroChip.text.toString())
                                            .set(animalData)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Animal añadido correctamente", Toast.LENGTH_SHORT).show()
                                                goToListadoActivity()
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
                        Toast.makeText(this, "Número de chip inválido", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToListadoActivity() {
        val intent = Intent(this, ListadoActivity::class.java)
        startActivity(intent)
        finish()  // Cerrar la actividad después de añadir el animal
    }
}





