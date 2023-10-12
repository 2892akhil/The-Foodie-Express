package com.akhil.wavesoffoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.akhil.wavesoffoods.Model.UserModel
import com.akhil.wavesoffoods.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity()
{

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initialize the firebase auth
        auth=Firebase.auth
        //initialize firebase database
        database=Firebase.database.reference
        binding.signButton.setOnClickListener {
            val intent=Intent(this, SignActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            // get text from edit text
            email=binding.editTextTextEmailAddress.text.toString().trim()
            password=binding.editTextTextPassword.text.toString().trim()
            if (email.isBlank() || password.isBlank())
            {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            } else
            {
                createUserAccount(email, password)

            }

        }
    }

    private fun createUserAccount(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                val user: FirebaseUser?=auth.currentUser
                Toast.makeText(this, "Login is Successfully", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {
                            val user: FirebaseUser?=auth.currentUser
                            Toast.makeText(
                                this,
                                "Create User & Login Successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            saveUserData()
                            updateUi(user)
                        } else
                        {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT)
                                .show()
                            Log.d(
                                "Account",
                                "createUserAccount: Authentication failed ",
                                task.exception
                            )
                        }

                    }
            }
        }
    }

    private fun updateUi(user: FirebaseUser?)
    {

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun saveUserData()
    {
        email=binding.editTextTextEmailAddress.text.toString().trim()
        password=binding.editTextTextPassword.text.toString().trim()

        val user=UserModel(name=null, email, password)
        val userId: String?=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    override fun onStart()
    {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser != null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}