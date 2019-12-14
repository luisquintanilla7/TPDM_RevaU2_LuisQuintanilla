package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class DoctorInsertar : AppCompatActivity() {
    var nombreDoc: EditText?=null
    var apellidoDoc: EditText?=null
    var edadDoc: EditText?=null
    var telDoc: EditText?=null
    var especialidad: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_insertar)

        nombreDoc=findViewById(R.id.campoNombre)
        apellidoDoc=findViewById(R.id.campoApellido)
        edadDoc=findViewById(R.id.campoEdad)
        telDoc=findViewById(R.id.campoTel)
        especialidad=findViewById(R.id.campoEspecialidad)
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
        nombreDoc?.setText("")
        apellidoDoc?.setText("")
        edadDoc?.setText("")
        telDoc?.setText("")
        especialidad?.setText("")
    }

    fun validaCampos(): Boolean{
        if(nombreDoc?.text!!.toString().isEmpty()||apellidoDoc?.text!!.isEmpty()||edadDoc?.text!!.isEmpty()||telDoc?.text!!.isEmpty()||especialidad?.text!!.isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun insertar(){
        try {
            var trans = Basedatos.writableDatabase
            var SQL = "INSERT INTO DOCTOR VALUES(NULL,'NOMBREDOC','APELLIDODOC','EDADDOC','TELDOC','ESPECIALIDAD')"
            if (validaCampos() == false) {
                mensaje("Error!", "Existe algun campo vacio ")
                return
            }
            SQL = SQL.replace("NOMBREDOC", nombreDoc?.text.toString())
            SQL = SQL.replace("APELLIDODOC", apellidoDoc?.text.toString())
            SQL = SQL.replace("EDADDOC", edadDoc?.text.toString())
            SQL = SQL.replace("TELDOC", telDoc?.text.toString())
            SQL = SQL.replace("ESPECIALIDAD", especialidad?.text.toString())

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
