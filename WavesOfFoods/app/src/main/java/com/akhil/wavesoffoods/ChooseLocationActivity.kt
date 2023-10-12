package com.akhil.wavesoffoods

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.akhil.wavesoffoods.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity()
{
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList=
            arrayOf("bisalpur", "bareilly", "pilibhit", "raipur", "rampur")
        val adapter=ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView=binding.locationList
        autoCompleteTextView.setAdapter(adapter)
        binding.locationButton.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}