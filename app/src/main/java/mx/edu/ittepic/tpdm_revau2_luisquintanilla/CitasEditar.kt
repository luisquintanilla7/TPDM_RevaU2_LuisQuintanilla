package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CitasEditar : AppCompatActivity() {
    var motivo: EditText?=null
    var fecha: EditText?=null
    var departamento: EditText?=null
    var diagnostico: EditText?=null
    var tratamiento: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        motivo=findViewById(R.id.campoMotivo)
        fecha=findViewById(R.id.campoFecha)
        departamento=findViewById(R.id.campoDep)
        diagnostico=findViewById(R.id.campoDiag)
        tratamiento=findViewById(R.id.campoTrat)
        botonInsertar=findViewById(R.id.botonInsertar)
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
            .setMessage("ESCRIBA EL ID A buscar  ").setView(campo)
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
            var SQL = "SELECT * FROM CITAS WHERE IDDOCTOR=" + id
            var respuesta = transaccion.rawQuery(SQL, null)
            if (respuesta.moveToFirst() == true) {
                var cadena = "MOTIVO: " + respuesta.getString(1) +
                        "\nFECHA: " + respuesta.getString(2) +
                        "\nDEPARTAMENTO: " + respuesta.getString(3) +
                        "\nDIAGNOSTICO: " + respuesta.getString(4) +
                        "\nTRATAMIENTO: " + respuesta.getString(5)
            }
            transaccion.close()
            motivo?.setText(respuesta.getString(1))
            fecha?.setText(respuesta.getString(2))
            departamento?.setText(respuesta.getString(3))
            diagnostico?.setText(respuesta.getString(4))
            tratamiento?.setText(respuesta.getString(5))

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
                "UPDATE CITAS SET MOTIVO='MOTIVO', FECHA='FECHA', DEPARTAMENTO='DEPARTAMENTO', DIAGNOSTICO='DIAGNOSTICO', TRATAMIENTO='TRATAMIENTO' WHERE IDCITAS=IDCITAS "

            if (validaCampos() == false) {
                mensaje("ERROR", "AL PARECER HAY UN CAMPO DE TEXTO VACIO")

            } else {

                SQL = SQL.replace("MOTIVO", motivo?.text.toString())
                SQL = SQL.replace("FECHA", fecha?.text.toString())
                SQL = SQL.replace("DEPARTAMENTO", departamento?.text.toString())
                SQL = SQL.replace("DIAGNOSTICO", diagnostico?.text.toString())
                SQL = SQL.replace("TRATAMIENTO", tratamiento?.text.toString())

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
        if (motivo?.text!!.toString().isEmpty() || fecha?.text!!.isEmpty() || departamento?.text!!.isEmpty() || diagnostico?.text!!.isEmpty() || tratamiento?.text!!.isEmpty()) {
            return false
        } else {
            return true
        }
    }

    fun limpiar() {
        motivo?.setText("")
        fecha?.setText("")
        departamento?.setText("")
        diagnostico?.setText("")
        tratamiento?.setText("")
    }
}
