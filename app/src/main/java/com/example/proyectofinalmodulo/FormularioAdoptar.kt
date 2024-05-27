package com.example.proyectofinalmodulo

data class FormularioAdoptar(
    val nombreCompleto: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val emailAdoptante: String = "",
    val ocupacion: String = "",
    val tipoVivienda: String = "",
    val tieneJardin: Boolean = false,
    val otrosAnimales: Boolean = false,
    val descripcionOtrosAnimales: String = "",
    val motivoAdopcion: String = "",
    val experienciaAnimales: String = ""
)
