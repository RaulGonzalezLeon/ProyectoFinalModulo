package com.example.proyectofinalmodulo

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

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
            R.id.cerrar_sesion -> {
                    actividadActual = 2
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true

            }
            R.id.eliminar_animal -> {
                    actividadActual = 3
                    val intent = Intent(this, EliminarAnimales::class.java)
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
}