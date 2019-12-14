package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BaseDatos(context: Context,
                name: String?,
                factory: SQLiteDatabase.CursorFactory?,
                version: Int): SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE DOCTOR(IDDOCTOR INTEGER PRIMARY KEY AUTOINCREMENT, NOMBREDOC VARCHAR(400),APELLIDODOC VARCHAR(400), EDADDOC INTEGER, TELDOC INTEGER,  ESPECIALIDAD VARCHAR(400) )")
        p0?.execSQL("CREATE TABLE PACIENTES (IDPACIENTE INTEGER PRIMARY KEY AUTOINCREMENT,  NOMBREPAC VARCHAR(400),APELLIDOPAC VARCHAR(400), EDAD INTEGER,  SEXO BOOLEAN, TELEFONO INTEGER )")
        p0?.execSQL("CREATE TABLE CITAS(IDCITA INTEGER PRIMARY KEY AUTOINCREMENT, MOTIVO VARCHAR (400), FECHA VARCHAR(400), DEPARTAMENTO VARCHAR (400), DIAGNOSTICO VARCHAR (400), TRATAMIENTO VARCHAR(400), FOREIGN KEY (IDDOCTOR) REFERENCES DOCTOR(IDDOCTOR), FOREIGN KEY (IDPACIENTE) REFERENCES PACIENTE(IDPACIENTE))")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}
