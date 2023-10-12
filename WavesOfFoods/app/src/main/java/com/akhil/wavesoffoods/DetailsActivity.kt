package com.akhil.wavesoffoods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akhil.wavesoffoods.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName=intent.getStringExtra("MenuItemName")
        val foodImage=intent.getIntExtra("MenuItemImage",0)
        binding.detailFoodName.text=foodName
        binding.detailFoodImage.setImageResource(foodImage)
        binding.imageButton.setOnClickListener {
            finish()
        }

    }
}