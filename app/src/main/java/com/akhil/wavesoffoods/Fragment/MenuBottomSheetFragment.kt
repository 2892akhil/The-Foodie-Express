package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.Model.MenuItem
import com.akhil.wavesoffoods.adapter.MenuAdapter
import com.akhil.wavesoffoods.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment()
{

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        binding=FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()
        return binding.root
    }

    private fun retrieveMenuItems()
    {
        database=FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference=database.reference.child("Menu")
        menuItems=mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                for (foodSnapshot in snapshot.children)
                {
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                Log.d("ITEMS", "onDataChange: Data Received")
                // set adapter
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError)
            {
            }

        })
    }

    private fun setAdapter()
    {
        if (menuItems.isNotEmpty())
        {
            val adapter=MenuAdapter(menuItems, requireContext())
            binding.MenuRecyclerView.layoutManager=LinearLayoutManager(requireContext())
            binding.MenuRecyclerView.adapter=adapter
        } else
        {
            Log.d("ITEMS", "setAdapter: Data not Set")
        }

    }

    companion object
    {

    }

}