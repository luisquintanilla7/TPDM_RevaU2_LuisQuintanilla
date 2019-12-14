package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class PacienteInsertar : AppCompatActivity() {
    var nombrePac: EditText?=null
    var apellidoPac: EditText?=null
    var edadPac: EditText?=null
    var telPac: EditText?=null
    var sexoPac: RadioGroup?=null
    var opHombre: RadioButton?=null
    var opMujer: RadioButton?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente_insertar)

        nombrePac=findViewById(R.id.campoNombre)
        apellidoPac=findViewById(R.id.campoApellido)
        edadPac=findViewById(R.id.campoEdad)
        telPac=findViewById(R.id.campoTel)
        opHombre=findViewById(R.id.hombre)
        opMujer=findViewById(R.id.mujer)
        sexoPac=findViewById(R.id.sexo)
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
        nombrePac?.setText("")
        apellidoPac?.setText("")
        edadPac?.setText("")
        telPac?.setText("")
        sexoPac = null
    }

    fun validaCampos(): Boolean{
        if(nombrePac?.text!!.isEmpty()||apellidoPac?.text!!.isEmpty()||edadPac?.text!!.isEmpty()||telPac?.text!!.isEmpty()||sexoPac?.toString()!!.isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun insertar(){
        try {
            var trans = Basedatos.writableDatabase
            var SQL = "INSERT INTO DOCTOR VALUES(NULL,'NOMBREPAC','APELLIDOPAC','EDADDOC','TELDOC','SEXOPAC')"
            if (validaCampos() == false) {
                mensaje("Error!", "Existe algun campo vacio ")
                return
            }
            SQL = SQL.replace("NOMBREPAC", nombrePac?.text.toString())
            SQL = SQL.replace("APELLIDOPAC", apellidoPac?.text.toString())
            SQL = SQL.replace("EDADPAC", edadPac?.text.toString())
            SQL = SQL.replace("TELPAC", telPac?.text.toString())

           // SQL = SQL.replace("SEXOPAC", sexoPac?.*.toString())
            trans.execSQL(SQL)
            trans.close()
            mensaje("Registro exitoso!", "Se inserto correctamente")
            limpiarCampos()
        }
        catch (er: SQLException) {
            mensaje("Error!","No se pudo insertar el registro, verifique sus datos!")
        }
    }

}
