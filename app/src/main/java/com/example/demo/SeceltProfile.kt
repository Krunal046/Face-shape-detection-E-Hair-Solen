package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.demo.databinding.ActivityHomeBinding
import com.example.demo.ui.dashboard.DashboardFragment

class SeceltProfile : AppCompatActivity() {

   /* private lateinit var rbUser: RadioButton
    private lateinit var rbBarber: RadioButton*/
    private lateinit var btnSelectContinue:Button

    private lateinit var cdUser:CardView
    private lateinit var cdBarber:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secelt_profile)
        supportActionBar?.hide()

     /*   rbUser = findViewById(R.id.rb_user)
        rbBarber = findViewById(R.id.rb_barber)
*/

        cdUser = findViewById(R.id.cd_user)
        cdBarber = findViewById(R.id.cd_barber)

      /*  btnSelectContinue = findViewById(R.id.btn_select_continue)*/


        cdUser.setOnClickListener {
            val intent = Intent(this@SeceltProfile, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Select the Users profile", Toast.LENGTH_SHORT).show()
        }

        cdBarber.setOnClickListener {
            val intent = Intent(this@SeceltProfile, Temp::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Select the Barbers profile", Toast.LENGTH_SHORT).show()
        }

    /*    btnSelectContinue.setOnClickListener {*/

     /*   if(rbUser.isChecked) {
            val intent = Intent(this@SeceltProfile, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "You Select the Users profile", Toast.LENGTH_SHORT).show()
        }
           else if (rbBarber.isChecked){
            Toast.makeText(this, "You Select the Barbers profile", Toast.LENGTH_SHORT).show()
            }
          else  if(rbBarber.isChecked && rbUser.isChecked){
            Toast.makeText(this, "Select only one of the this profile", Toast.LENGTH_SHORT).show()
            }
            else{
            Toast.makeText(this, "Please Select the one profile", Toast.LENGTH_SHORT).show()
            }*/
     /*   }*/

    }
}