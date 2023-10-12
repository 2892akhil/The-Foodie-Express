package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.MenuAdapter
import com.akhil.wavesoffoods.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class MenuBottomSheetFragment : BottomSheetDialogFragment()
{

     private lateinit var binding: FragmentMenuBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
       binding=FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        val menuFoodName=listOf("Herbal Pancake","Green Noddle","Ice Cream","Soup")
        val menuItemPrice=listOf("$5","$7","$8","$9")
        val menuItemImage=listOf(
            R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,)
        val adapter=MenuAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuItemImage),requireContext())
        binding.MenuRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.MenuRecyclerView.adapter=adapter

    return binding.root
    }

    companion object
    {

    }
}