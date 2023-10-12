package com.akhil.wavesoffoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.akhil.wavesoffoods.Model.UserModel
import com.akhil.wavesoffoods.R.*
import com.akhil.wavesoffoods.databinding.ActivitySignBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity() : AppCompatActivity(), Parcelable
{
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }

    constructor(parcel: Parcel) : this()
    {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {

    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignActivity>
    {
        override fun createFromParcel(parcel: Parcel): SignActivity
        {
            return SignActivity(parcel)
        }

        override fun newArray(size: Int): Array<SignActivity?>
        {
            return arrayOfNulls(size)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=Firebase.auth
        database=Firebase.database.reference
        binding.alreadyAccount.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.createAccount.setOnClickListener {
            username=binding.name.text.toString().trim()
            email=binding.emailuser.text.toString().trim()
            password=binding.passworduser.text.toString().trim()
            if (username.isBlank() || email.isBlank() || password.isBlank())
            {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_LONG).show()
            } else
            {
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String)
    {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent=Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else
            {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }

    private fun saveUserData()
    {
        username=binding.name.text.toString().trim()
        email=binding.emailuser.text.toString().trim()
        password=binding.passworduser.text.toString().trim()
        val user=UserModel(username, email, password)
        val userId: String=FirebaseAuth.getInstance().currentUser!!.uid
        //save user data firebase database
        database.child("user").child(userId).setValue(user)
    }
}