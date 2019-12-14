package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.content.Intent
import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class RegistroCitas : AppCompatActivity() {
    var btnInserta: Button?=null
    var btnEditar: Button?=null
    var btnEliminar: Button?=null
    var btnActualizar: Button?=null
    var mostrarList : TextView?= null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_citas)
        btnInserta=findViewById(R.id.insertar)
        btnEditar=findViewById(R.id.editar)
        btnActualizar=findViewById(R.id.actualizar)
        btnEliminar=findViewById(R.id.eliminar)
        mostrarList=findViewById(R.id.mostrarCampo)
        mostrar()

        btnInserta?.setOnClickListener {
            val ventanaTarea = Intent(this,PacienteInsertar::class.java)
            startActivity(ventanaTarea)

        }
        btnEditar?.setOnClickListener {
            val ventanaTarea = Intent(this,PacienteEditar::class.java)
            startActivity(ventanaTarea)
        }

        btnEliminar?.setOnClickListener {
            pedirID(btnEliminar?.text.toString())

        }
        btnActualizar?.setOnClickListener {
            mostrar()
        }
    }
    fun mostrar(){
        var dato = ""
        try {
            var transicion = Basedatos.readableDatabase
            var con = "SELECT * FROM CITAS"
            var cur = transicion.rawQuery(con,null)
            if(cur != null) {
                if (cur.moveToFirst() == true) {
                    do{
                        dato +="ID: ${cur.getString(0)}\nMotivo: ${cur.getString(1)}\nFecha: ${cur.getString(2)}\nDepartamento:${cur.getString(3)}\n "+
                                "Diagnostico:${cur.getString(4)}\n Tratamiento:${cur.getString(5)}\n "
                    }while (cur.moveToNext())
                    mostrarList?.setText(dato)
                }else{
                    mensaje("Advertencia!","No existen listas")
                }
            }
            cur.close()
        }
        catch (er: SQLException){
            mensaje("Error!","No se encuentran registros en la BD")
        }
    }
    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }

    fun pedirID(etiqueta:String){
        var elemento = EditText(this)
        elemento.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("Atención!").setMessage("Escriba el ID en ${etiqueta}: ").setView(elemento)
            .setPositiveButton("OK"){dialog,which ->
                if(validarCampo(elemento) == false){
                    Toast.makeText(this, "Error! campo vacío", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                buscar(elemento.text.toString(),etiqueta)

            }.setNeutralButton("Cancelar"){dialog, which ->  }.show()
    }

    fun validarCampo(elemento: EditText): Boolean{
        if(elemento.text.toString().isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun buscar(id: String, btnEtiqueta: String){
        try {
            var trans = Basedatos.readableDatabase
            var con="SELECT * FROM PACIENTE WHERE IDPACIENTE="+id
            var  cur = trans.rawQuery(con,null)

            if (cur.moveToFirst()==true){
                if (btnEtiqueta.startsWith("e")){
                    var dato = "¿Seguro de eliminar Cita?: \"${cur.getString(1)}\" con el ID \"${cur.getString(0)}\" ?"
                    var alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Atención").setMessage(dato).setNeutralButton("NO"){dialog,which->
                        return@setNeutralButton
                    }.setPositiveButton("SI"){dialog,which->
                        eliminar(id)
                    }.show()
                }
            }else{
                mensaje("Error!","No existe el id: ${id}")
            }
        }catch (err: java.sql.SQLException){
            mensaje("Error!","No se encontro el registro")
        }
    }

    fun eliminar(id:String){
        try{
            var trans = Basedatos.writableDatabase
            var SQL = "DELETE FROM CITAS WHERE IDCITAS="+id
            trans.execSQL(SQL)
            trans.close()
            mensaje("Exito!", "Se elimino correctamente el id: ${id}")

        }catch (err: java.sql.SQLException){
            mensaje("Error!", "No se pudo eliminar")

        }
    }
}
