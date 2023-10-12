package com.akhil.wavesoffoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.akhil.wavesoffoods.databinding.ActivitySignBinding

class SignActivity() : AppCompatActivity(), Parcelable
{
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
        binding.alreadyAccount.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}