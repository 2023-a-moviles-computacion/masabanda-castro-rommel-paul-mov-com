package com.example.examenrommel

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var arregloL : ArrayAdapter<LigaDeFutbol>
    private lateinit var ligas: ArrayList<LigaDeFutbol>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de la base de datos SQLite
        EBaseDeDatos.BDatos = ESqliteHelper(this)

        // Obtener las ligas desde la BD
        ligas = obtenerLigaDesdeLaBD()

        // Configurar ListView y su adaptador
        val listViewLigas = findViewById<ListView>(R.id.list_view_ligas)
        arregloL = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // diseño del elemento individual
            ligas // vincular fuente de datos al adaptador
        )
        listViewLigas.adapter = arregloL //conectar adapter a lv
        arregloL!!.notifyDataSetChanged() //actualizar lv

        // Utilizar un menu contextual en el listview
        registerForContextMenu(listViewLigas)

        val botonCrearLigaListView = findViewById<Button>(R.id.btn_crear_liga)
        botonCrearLigaListView
            .setOnClickListener {
                irActividad(FormularioLiga::class.java)
            }
    }

    // Cuando la actividad se renauda
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val ligasActualizadas = obtenerLigaDesdeLaBD()
        arregloL.clear()
        arregloL.addAll(ligasActualizadas)
        arregloL.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eliminarLiga(id: Int):Boolean{
        val dbHelper = ESqliteHelper(this)
        val conf = dbHelper.eliminarLigaFormulario(id)
        dbHelper.close()
        return conf
    }

    // Crear menu contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

    }

    // Manejar las opciones del menu contextual
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val ligaSeleccionada = ligas[posicionSeleccionada]
        val idSeleccionado = ligaSeleccionada.id

        return when (item.itemId){
            R.id.mi_editar ->{
                val intent = Intent(this, FormularioLigaEditar::class.java)
                // Agregar un dato extra "ligaID" es el objeto LigaFutbol y pasarlo a la actividad
                intent.putExtra("ligaID", ligaSeleccionada)
                startActivity(intent)
                return true
            }
            R.id.mi_eliminar->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Desea eliminar")
                builder.setPositiveButton(
                    "Aceptar",
                    DialogInterface.OnClickListener{
                            dialog, which -> eliminarLiga(idSeleccionado)
                            ligas.removeAt(posicionSeleccionada)
                            arregloL.notifyDataSetChanged()
                    }
                )
                builder.setNegativeButton(
                    "Cancelar",
                    null
                )
                val  dialogo = builder.create()
                dialogo.show()

                return true
            }
            R.id.mi_ver_liga->{

                val intent = Intent(this, ListViewEquipos::class.java)
                intent.putExtra("ligaID", ligaSeleccionada)
                startActivity(intent)
                return true

            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase);
        startActivity(intent);
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerLigaDesdeLaBD(): ArrayList<LigaDeFutbol> {
        //crear instancia a sqlite para acceder a sus datos
        val dbHelper = ESqliteHelper(this)
        val ligas = dbHelper.obtenerTodasLasLigas()
        dbHelper.close()
        return ligas
    }
}