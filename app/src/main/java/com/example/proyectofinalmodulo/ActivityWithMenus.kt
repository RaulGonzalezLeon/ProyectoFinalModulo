package com.example.proyectofinalmodulo

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalmodulo.AccionesAnimales.EliminarAnimales
import com.example.proyectofinalmodulo.AccionesAnimales.InsertarAnimales
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

open class ActivityWithMenus: AppCompatActivity() {
    companion object{
        var actividadActual = 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal,menu)



        for (i in 0 until menu.size()){
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true

        }
        detectarPermisos(menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.mostrar_listado -> {
                    actividadActual = 0
                    val intent = Intent(this, ListadoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
            }
            R.id.agregar_animal -> {

                    actividadActual = 1
                    val intent = Intent(this, InsertarAnimales::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
            }
            R.id.eliminar_animal -> {
                actividadActual = 2
                val intent = Intent(this, EliminarAnimales::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
                true

            }
            R.id.cerrar_sesion -> {
                    actividadActual = 3
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true

            }
            R.id.formulario_adoptar -> {
                    actividadActual = 4
                    val intent = Intent(this, InsertarFormularioAdoptar::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun detectarPermisos(menu: Menu) {
        var rolUsuario  = ""
        val db = FirebaseFirestore.getInstance()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        if (currentUserEmail != null) {
            db.collection("usuarios")
                .document(currentUserEmail)
                .get()
                .addOnSuccessListener { document ->
                    rolUsuario = document.getString("Rol").toString()
                    if(rolUsuario == "Usuario"){
                        menu.getItem(1).isVisible = false
                        menu.getItem(2).isVisible = false
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Usuario", "Error al obtener el rol del usuario", exception)
                }
        }
    }

}