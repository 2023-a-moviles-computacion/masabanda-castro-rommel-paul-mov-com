package com.example.exameniib_masabanda

import android.app.Activity
import android.app.AlertDialog
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ListViewEquipos : AppCompatActivity() {

    var listaEquiposFireBase: ArrayList<EquipoFutbol> = arrayListOf<EquipoFutbol>()
    var idItemSeleccionado = 0
    var idLigaFutbol: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val callBackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){

                    val data = result.data
                    val db = Firebase.firestore

                    val equipoRef = db.collection("liga_futbol")
                        .document(data?.getStringExtra("idLigaDevuelto").toString())
                        .collection("equipos")
                        .document(data?.getStringExtra("idDevuelto").toString())

                    val equipoActualizado = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "campeonatos" to data?.getIntExtra("campeonatosModificado",0),
                        "estadio" to data?.getBooleanExtra("estadioModificado",false),
                        "precio" to data?.getDoubleExtra("precioModificado", 0.0),
                        "fechaCreacion" to data?.getStringExtra("fechaCreacionModificada").toString()
                    )

                    equipoRef.set(
                        equipoActualizado
                    )
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val callBackContenidoIntentExplicito1 =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){

                    val data = result.data
                    val db = Firebase.firestore

                    val equiposRefUnica = db.collection("liga_futbol").document(this.idLigaFutbol!!).collection("equipos")

                    val equipoActualizado = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "campeonatos" to data?.getIntExtra("campeonatosModificado",0),
                        "estadio" to data?.getBooleanExtra("estadioModificado",false),
                        "precio" to data?.getDoubleExtra("precioModificado", 0.0),
                        "fechaCreacion" to data?.getStringExtra("fechaCreacionModificada").toString()
                    )

                    equiposRefUnica
                        .add(equipoActualizado)
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_equipos)

        this.idLigaFutbol = intent.getStringExtra("idLiga")

        val textNombreLiga = findViewById<TextView>(R.id.tv_nombre_padre)
        textNombreLiga.setText(intent.getStringExtra("nombreLiga"))

        actualizarListView()

        val botonCrearEquipoListView = findViewById<Button>(R.id.btn_crear_equipo)
        botonCrearEquipoListView
            .setOnClickListener {
                irActividad(FormularioEquipo::class.java)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarListView(){

        val listViewE = findViewById<ListView>(R.id.listView_equipos)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEquiposFireBase!!
        )

        listViewE.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listViewE)

        consultarEquipo(adaptador)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun consultarEquipo(adaptador: ArrayAdapter<EquipoFutbol>) {
        val db = Firebase.firestore
        val equipoRefUnico = db.collection("liga_futbol").document(this.idLigaFutbol!!).collection("equipos")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        equipoRefUnico
            .get()
            .addOnSuccessListener {
                for (equipo in it){
                    anadirEquipo(equipo, equipo.id)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun anadirEquipo(equipo: QueryDocumentSnapshot, idEquipo: String) {
        val nuevoEquipo = EquipoFutbol(
            idEquipo,
            equipo.data.get("nombre").toString() as String,
            equipo.data.get("campeonatos").toString().toInt() as Int,
            equipo.data.get("estadio").toString().toBoolean() as Boolean,
            equipo.data.get("precio").toString().toDouble() as Double,
            equipo.data.get("fechaCreacion").toString() as String
        )

        this.listaEquiposFireBase.add(nuevoEquipo)
    }

    private fun limpiarArreglo() {
        this.listaEquiposFireBase.clear()
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
        return when(item.itemId){
            R.id.mi_editarE ->{
                abrirConParametrosEditarEquipo(FormularioEquipoEditar::class.java)
                return true
            }
            R.id.mi_eliminarE ->{
                abrirDialogoEliminar()
                actualizarListView()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun abrirConParametrosEditarEquipo(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val equipo = listaEquiposFireBase?.get(idItemSeleccionado)

        intentExplicito.putExtra("idLiga", this.idLigaFutbol)
        intentExplicito.putExtra("id", equipo?.id)
        intentExplicito.putExtra("nombre", equipo?.nombre)
        intentExplicito.putExtra("campeonatos", equipo?.campeonatos)
        intentExplicito.putExtra("estadio", equipo?.poseeEstadio)
        intentExplicito.putExtra("precio", equipo?.precioDelEquipo)
        intentExplicito.putExtra("fechaCreacion", equipo?.fechaDeCreacionTexto)


        callBackContenidoIntentExplicito.launch(intentExplicito)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        intent.putExtra("id", this.idLigaFutbol)
        callBackContenidoIntentExplicito1.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun abrirDialogoEliminar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton("Aceptar") {
                dialog, which ->

            val equipo = listaEquiposFireBase.get(idItemSeleccionado)
            val db = Firebase.firestore

            val relojRef = db.collection("liga_futbol")
                .document(this.idLigaFutbol!!)
                .collection("equipos")
                .document(equipo.id!!)

            relojRef
                .delete()
                .addOnSuccessListener{}
                .addOnFailureListener {  }

            actualizarListView()

        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }
}