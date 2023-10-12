package com.akhil.wavesoffoods.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akhil.wavesoffoods.MainActivity
import com.akhil.wavesoffoods.databinding.FragmentCongratesBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CongratesBottomSheet : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentCongratesBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        binding=FragmentCongratesBottomSheetBinding.inflate(layoutInflater, container, false)
        binding.goHome.setOnClickListener {
            val intent=Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object
    {

    }
}