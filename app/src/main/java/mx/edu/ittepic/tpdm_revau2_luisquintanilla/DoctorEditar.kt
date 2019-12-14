package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class DoctorEditar : AppCompatActivity() {
    var nombreDoc: EditText?=null
    var apellidoDoc: EditText?=null
    var edadDoc: EditText?=null
    var telDoc: EditText?=null
    var especialidad: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_editar)
        nombreDoc = findViewById(R.id.campoNombre)
        apellidoDoc = findViewById(R.id.campoApellido)
        edadDoc = findViewById(R.id.campoEdad)
        telDoc = findViewById(R.id.campoTel)
        especialidad = findViewById(R.id.campoEspecialidad)
        botonInsertar = findViewById(R.id.botonInsertar)
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
                var SQL = "SELECT * FROM DOCTOR WHERE IDDOCTOR=" + id
                var respuesta = transaccion.rawQuery(SQL, null)
                if (respuesta.moveToFirst() == true) {
                    var cadena = "NOMBREDOC: " + respuesta.getString(1) +
                            "\nAPELLIDODOC: " + respuesta.getString(2) +
                            "\nEDADDOC: " + respuesta.getString(3) +
                            "\nTELDOC: " + respuesta.getString(4) +
                            "\nESPECIALIDAD: " + respuesta.getString(5)
                }
                transaccion.close()
                nombreDoc?.setText(respuesta.getString(1))
                apellidoDoc?.setText(respuesta.getString(2))
                edadDoc?.setText(respuesta.getString(3))
                telDoc?.setText(respuesta.getString(4))
                especialidad?.setText(respuesta.getString(5))

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
                    "UPDATE DOCTOR SET NOMBREDOC='CAMPONOMBRE', APELLIDODOC='CAMPOAPELIDO', EDADDOC='CAMPOEDAD', TELDOC='CAMPOTEL', ESPECIALIDAD='CAMPOESPECIALIDAD' WHERE IDDOCTOR=IDDOCTOR "

                if (validaCampos() == false) {
                    mensaje("ERROR", "AL PARECER HAY UN CAMPO DE TEXTO VACIO")

                } else {

                    SQL = SQL.replace("NOMBREDOC", nombreDoc?.text.toString())
                    SQL = SQL.replace("APELLIDODOC", apellidoDoc?.text.toString())
                    SQL = SQL.replace("EDADDOC", edadDoc?.text.toString())
                    SQL = SQL.replace("TELDOC", telDoc?.text.toString())
                    SQL = SQL.replace("ESPECIALIDAD", especialidad?.text.toString())

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
            if (nombreDoc?.text!!.toString().isEmpty() || apellidoDoc?.text!!.isEmpty() || edadDoc?.text!!.isEmpty() || telDoc?.text!!.isEmpty() || especialidad?.text!!.isEmpty()) {
                return false
            } else {
                return true
            }
        }

        fun limpiar() {
            nombreDoc?.setText("")
            apellidoDoc?.setText("")
            edadDoc?.setText("")
            telDoc?.setText("")
            especialidad?.setText("")
        }
}
