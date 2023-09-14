package com.example.examenrommel

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

class ListViewEquipos : AppCompatActivity() {

    private lateinit var  arregloE: ArrayAdapter<EquipoFutbol>
    private lateinit var  equipo: ArrayList<EquipoFutbol>
    var idLigaAux = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_equipos)

        EBaseDeDatos.BDatos = ESqliteHelper(this)

        //Recibir el obj LigaFutbol aatravés del intent
        val liga = intent.getSerializableExtra("ligaID") as LigaDeFutbol
        val ligaID = liga.id

        //Colocar padre de titulo
        val namePadre = findViewById<TextView>(R.id.tv_nombre_padre)
        namePadre.setText(liga.nombre)

        if(ligaID != -1){
            equipo = obtenerEquipoDeLiga(ligaID)

            val listView = findViewById<ListView>(R.id.listView_equipos)
            arregloE = ArrayAdapter(this, android.R.layout.simple_list_item_1, equipo)
            listView.adapter = arregloE
            arregloE.notifyDataSetChanged()

            registerForContextMenu(listView)
            idLigaAux = ligaID

        } else{
            Toast.makeText(this, "Error al obtener el ID de la liga", Toast.LENGTH_SHORT).show()
        }

        val botonCrearEquipoListView = findViewById<Button>(R.id.btn_crear_equipo)
        botonCrearEquipoListView
            .setOnClickListener {
                val intent =Intent(this, FormularioEquipo::class.java)
                intent.putExtra("idLiga",idLigaAux)
                startActivity(intent)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val equipoActualizado = obtenerEquipoDeLiga(idLigaAux)
        arregloE.clear()
        arregloE.addAll(equipoActualizado)
        arregloE.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eliminarEquipo(id:Int):Boolean{
        val dbHelper = ESqliteHelper(this)
        val conf = dbHelper.eliminarEquipoFormulario(id)
        dbHelper.close()
        return conf
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menuequipo, menu)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {

        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicionSeleccionada = info.position
        val equipoSeleccionado = equipo[posicionSeleccionada]
        val idEquipoSeleccionado = equipoSeleccionado.id

        return when (item.itemId){
            R.id.mi_editar ->{
                val intent = Intent(this, FormularioEquipoEditar::class.java)
                intent.putExtra("equipoID", equipoSeleccionado)
                startActivity(intent)
                return true
            }
            R.id.mi_eliminar->{
                //"${idItemSeleccionado}"
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Desea eliminar")
                builder.setPositiveButton(
                    "Aceptar",
                    DialogInterface.OnClickListener{
                            dialog, which -> eliminarEquipo(idEquipoSeleccionado)
                            equipo.removeAt(posicionSeleccionada)
                            arregloE.notifyDataSetChanged()
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
            else -> super.onContextItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerEquipoDeLiga(equipoId: Int): ArrayList<EquipoFutbol> {
        val dbHelperEquipo = ESqliteHelper(this)
        val equipo = dbHelperEquipo.obtenerEquipoDeLiga(equipoId)
        dbHelperEquipo.close()
        return equipo
    }

}