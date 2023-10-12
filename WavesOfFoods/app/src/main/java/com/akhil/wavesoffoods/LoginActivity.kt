package com.akhil.wavesoffoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akhil.wavesoffoods.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity()
{
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signButton.setOnClickListener{
            val intent= Intent(this,SignActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val intent=Intent(this,ChooseLocationActivity::class.java)
            startActivity(intent)
        }

    }
}