package com.akhil.wavesoffoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.akhil.wavesoffoods.Fragment.NotificationBottomFragment
import com.akhil.wavesoffoods.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var user:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user=FirebaseAuth.getInstance()

        val NavController: NavController=findNavController(R.id.fragmentContainerView)
        val bottomnav: BottomNavigationView=
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.setupWithNavController(NavController)
        binding.gantaButton.setOnClickListener {
            val bottomSheetDialog=NotificationBottomFragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
        binding.logout.setOnClickListener {
            user.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}