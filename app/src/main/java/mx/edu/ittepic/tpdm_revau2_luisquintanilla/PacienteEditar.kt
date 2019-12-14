package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_paciente_editar.*

class PacienteEditar : AppCompatActivity() {
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
        setContentView(R.layout.activity_paciente_editar)
        nombrePac=findViewById(R.id.campoNombre)
        apellidoPac=findViewById(R.id.campoApellido)
        edadPac=findViewById(R.id.campoEdad)
        telPac=findViewById(R.id.campoTel)
        opHombre=findViewById(R.id.hombre)
        opMujer=findViewById(R.id.mujer)
        sexoPac=findViewById(R.id.sexo)
        botonInsertar=findViewById(R.id.botonInsertar)
        pedirID()
        botonInsertar?.setOnClickListener {
            actualiza()
        }
    }
    fun pedirID() {
        var campo = EditText(this)
        campo.inputType = InputType.TYPE_CLASS_NUMBER

        fun validar(text: EditText): Boolean {
            if (campo.text.toString().isEmpty()) {
                return false
            } else {
                return true
            }
        }
        AlertDialog.Builder(this).setTitle("ALERTA")
            .setMessage("ESCRIBA EL ID A   ").setView(campo)
            .setPositiveButton("OK") { dialog, which ->
                if (validar(campo) == false) {
                    Toast.makeText(this, "ERROR: CAMPO ID VACIO", Toast.LENGTH_LONG)
                        .show()
                    return@setPositiveButton
                }
                buscar(campo.text.toString())

            }
            .setNeutralButton("CANCELAR") { dialog, which -> }
            .show()
    }

    fun buscar(id: String) {
        try {
            var transaccion = Basedatos.readableDatabase
            var SQL = "SELECT * FROM PACIENTE WHERE IDPACIENTE=" + id
            var respuesta = transaccion.rawQuery(SQL, null)
            if (respuesta.moveToFirst() == true) {
                var cadena = "NOMBREPAC: " + respuesta.getString(1) +
                        "\nAPELLIDOPAC: " + respuesta.getString(2) +
                        "\nEDADPAC: " + respuesta.getString(3) +
                        "\nTELPAC: " + respuesta.getString(4) +
                        "\nSEXOPAC: " + respuesta.getString(5)
            }
            transaccion.close()
            nombrePac?.setText(respuesta.getString(1))
            apellidoPac?.setText(respuesta.getString(2))
            edadPac?.setText(respuesta.getString(3))
            telPac?.setText(respuesta.getString(4))
        //    sexoPac?.setText(respuesta.getString(5))

            botonInsertar?.setText("Aplicar Cambios")

        } catch (err: SQLiteException) {
            mensaje("ERROR", "NO SE PUEDE EJECUTAR EL SELECT")
        }
    }

    fun mensaje(a: String, b: String) {
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i -> }.show()
    }

    fun actualiza() {
        try {
            var transaccion = Basedatos.writableDatabase
            var SQL =
                "UPDATE DOCTOR SET NOMBREDOC='CAMPONOMBRE', APELLIDODOC='CAMPOAPELIDO', EDADDOC='CAMPOEDAD', TELDOC='CAMPOTEL', SEXOPAC='SEXOPAC' WHERE IDPACIENTE=IDPACIENTE"

            if (validaCampos() == false) {
                mensaje("ERROR", "AL PARECER HAY UN CAMPO DE TEXTO VACIO")

            } else {

                SQL = SQL.replace("NOMBREPAC", nombrePac?.text.toString())
                SQL = SQL.replace("APELLIDOPAC", apellidoPac?.text.toString())
                SQL = SQL.replace("EDADPAC", edadPac?.text.toString())
                SQL = SQL.replace("TELPAC", telPac?.text.toString())
                // SQL = SQL.replace("SEXOPAC", sexoPac?.*.toString())

                transaccion.execSQL(SQL)
                transaccion.close()
                limpiar()
                mensaje("EXITO", "SE INSERTO CORRECTAMENTE")

            }
        } catch (err: SQLiteException) {
            mensaje("Error", "NO SE PUDO INSERTAR, TAL VEZ ID YA EXISTE")
        }

    }
    fun validaCampos(): Boolean {
        if (nombrePac?.text!!.toString().isEmpty() || apellidoPac?.text!!.isEmpty() || edadPac?.text!!.isEmpty() || telPac?.text!!.isEmpty()) {  //sexoPac?.text!!.isEmpty()
            return false
        } else {
            return true
        }
    }

    fun limpiar() {
        nombrePac?.setText("")
        apellidoPac?.setText("")
        edadPac?.setText("")
        telPac?.setText("")
        sexoPac?.clearCheck()
    }
}
