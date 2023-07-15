package com.example.examenrommel

import android.annotation.SuppressLint
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

    @RequiresApi(Build.VERSION_CODES.O)
    val arreglo = BDMemoria.listaLigas
    var idItemSeleccionado = 0

    var adaptador: ArrayAdapter<LigaDeFutbol>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view_ligas)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listView.adapter = adaptador
        adaptador!!.notifyDataSetChanged()

        val botonCrearLigaListView = findViewById<Button>(R.id.btn_crear_liga)
        botonCrearLigaListView
            .setOnClickListener {
                irActividad(FormularioLiga::class.java)
            }
        registerForContextMenu(listView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        actualizarLista(adaptador!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eliminarLiga(){

        val iterador = BDMemoria.listaEquipos.iterator()
        while (iterador.hasNext()) {
            val equipo = iterador.next()
            if (equipo.fkLiga == this.arreglo[idItemSeleccionado].id) {
                iterador.remove() // Eliminar de forma segura utilizando el iterador
            }
        }
        arreglo.removeAt(idItemSeleccionado)
        actualizarLista(adaptador)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarLiga(){
        val ligaSeleccionada = arreglo[idItemSeleccionado]
        val intent = Intent(this, FormularioLigaEditar::class.java)
        intent.putExtra("liga", ligaSeleccionada)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun verEquiposLiga(){
        val ligaSeleccionada = arreglo[idItemSeleccionado]
        val intent = Intent(this, ListViewEquipos::class.java)
        intent.putExtra("liga", ligaSeleccionada)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarLista(
        adaptador: ArrayAdapter<LigaDeFutbol>?
    ){
        if (adaptador != null) {
            adaptador.notifyDataSetChanged()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                //"${idItemSeleccionado}"
                //irActividad(FormularioLiga::class.java)
                editarLiga()
                return true
            }
            R.id.mi_eliminar->{
                //"${idItemSeleccionado}"
                abrirDialogo()
                return true
            }
            R.id.mi_ver_liga->{
                //"${idItemSeleccionado}"
                verEquiposLiga()
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
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which -> eliminarLiga()
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val  dialogo = builder.create()
        dialogo.show()
    }
}