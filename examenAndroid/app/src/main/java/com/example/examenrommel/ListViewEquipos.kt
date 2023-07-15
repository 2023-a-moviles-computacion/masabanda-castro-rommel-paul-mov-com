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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

class ListViewEquipos : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    var arreglo = BDMemoria.listaEquipos
    var idItemSeleccionado = 0

    var adaptador: ArrayAdapter<EquipoFutbol>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_equipos)

        val ligaSeleccionada = intent.getSerializableExtra("liga") as LigaDeFutbol
        arreglo = BDMemoria.listaEquipos.filter { it.fkLiga == ligaSeleccionada.id }.toMutableList()

        //Colocar padre de titulo
        val namePadre = findViewById<TextView>(R.id.tv_nombre_padre)
        namePadre.setText(ligaSeleccionada.nombre)

        val listView = findViewById<ListView>(R.id.listView_equipos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador!!.notifyDataSetChanged()

        val botonCrearEquipoListView = findViewById<Button>(R.id.btn_crear_equipo)
        botonCrearEquipoListView
            .setOnClickListener {
                val idLigaPadre = ligaSeleccionada.id
                val intent =Intent(this, FormularioEquipo::class.java)
                intent.putExtra("idLiga",idLigaPadre)
                Log.i("TAG", "Mensaje informativo padre_ListView ${idLigaPadre}")
                startActivity(intent)
            }
        registerForContextMenu(listView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val ligaSeleccionada = intent.getSerializableExtra("liga") as LigaDeFutbol
        arreglo = BDMemoria.listaEquipos.filter { it.fkLiga == ligaSeleccionada.id }.toMutableList()
        adaptador?.clear()
        adaptador?.addAll(arreglo)
        adaptador?.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eliminarEquipo(){
        val equipoEliminado = arreglo[idItemSeleccionado]
        BDMemoria.listaEquipos.remove(equipoEliminado)
        arreglo.removeAt(idItemSeleccionado)
        actualizarLista(adaptador)
        adaptador?.clear()
        adaptador?.addAll(arreglo)
        adaptador?.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editarEquipo(){
        val equipoSeleccionado = arreglo[idItemSeleccionado]
        val intent = Intent(this, FormularioEquipoEditar::class.java)
        intent.putExtra("equipo", equipoSeleccionado)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarLista(
        adaptador: ArrayAdapter<EquipoFutbol>?
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
        inflater.inflate(R.menu.menuequipo, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                //"${idItemSeleccionado}"
                editarEquipo()
                return true
            }
            R.id.mi_eliminar->{
                //"${idItemSeleccionado}"
                abrirDialogo()
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
                    dialog, which -> eliminarEquipo()
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