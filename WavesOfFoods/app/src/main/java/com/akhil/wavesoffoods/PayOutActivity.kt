package com.akhil.wavesoffoods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akhil.wavesoffoods.Fragment.CongratesBottomSheet
import com.akhil.wavesoffoods.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity()
{
   lateinit var binding:ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editPlaceButton.setOnClickListener {
            val bottomSheetDialog=CongratesBottomSheet()

            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
    }
}