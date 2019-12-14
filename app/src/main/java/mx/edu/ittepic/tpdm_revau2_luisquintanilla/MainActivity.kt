package mx.edu.ittepic.tpdm_revau2_luisquintanilla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    var btnDoctor: Button? =null
    var btnPaciente: Button? =null
    var btnCitas: Button? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDoctor=findViewById(R.id.btnDoctor)
        btnPaciente=findViewById(R.id.btnPaciente)
        btnCitas=findViewById(R.id.btnCitas)

        //Ir a la ventana de doctores
        btnDoctor?.setOnClickListener {
            val ventanaDoctor = Intent(this, RegistroDoctores::class.java)
            startActivity(ventanaDoctor)
        }
        //Ir a la ventana de Pacientes
        btnDoctor?.setOnClickListener {
            val ventanaPaciente = Intent(this, RegistroPacientes::class.java)
            startActivity(ventanaPaciente)
        }
        //Ir a la ventana de Citas
        btnDoctor?.setOnClickListener {
            val ventanaCita = Intent(this, RegistroCitas::class.java)
            startActivity(ventanaCita)
        }



    }
}
