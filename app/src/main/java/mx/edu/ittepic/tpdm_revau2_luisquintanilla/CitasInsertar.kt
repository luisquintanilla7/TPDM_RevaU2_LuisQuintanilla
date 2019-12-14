package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class CitasInsertar : AppCompatActivity() {
    var motivo: EditText?=null
    var fecha: EditText?=null
    var departamento: EditText?=null
    var diagnostico: EditText?=null
    var tratamiento: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas_insertar)
        motivo=findViewById(R.id.campoMotivo)
        fecha=findViewById(R.id.campoFecha)
        departamento=findViewById(R.id.campoDep)
        diagnostico=findViewById(R.id.campoDiag)
        tratamiento=findViewById(R.id.campoTrat)
        botonInsertar=findViewById(R.id.botonInsertar)

        botonInsertar?.setOnClickListener {
            insertar()
        }
    }
    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }

    fun limpiarCampos(){
        motivo?.setText("")
        fecha?.setText("")
        departamento?.setText("")
        diagnostico?.setText("")
        tratamiento?.setText("")
    }

    fun validaCampos(): Boolean{
        if(motivo?.text!!.isEmpty()||fecha?.text!!.isEmpty()||departamento?.text!!.isEmpty()||diagnostico?.text!!.isEmpty()||tratamiento?.toString()!!.isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun insertar(){
        try {
            var trans = Basedatos.writableDatabase
            var SQL = "INSERT INTO CITAS VALUES(NULL,'MOTIVO','FECHA','DEPARTAMENTO','DIAGNOSTICO','TRATAMIENTO')"
            if (validaCampos() == false) {
                mensaje("Error!", "Existe algun campo vacio ")
                return
            }

            SQL = SQL.replace("MOTIVO", motivo?.text.toString())
            SQL = SQL.replace("FECHA", fecha?.text.toString())
            SQL = SQL.replace("DEPARTAMENTO", departamento?.text.toString())
            SQL = SQL.replace("DIAGNOSTICO", diagnostico?.text.toString())
            SQL = SQL.replace("TRATAMIENTO", tratamiento?.text.toString())
            trans.execSQL(SQL)
            trans.close()
            mensaje("Registro exitoso!", "Se inserto correctamente")
            limpiarCampos()
        }
        catch (er: SQLException) {
            mensaje("Error!", "No se pudo insertar el registro, verifique sus datos!")
        }
    }
}
