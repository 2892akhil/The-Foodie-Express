package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.Model.MenuItem
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.MenuAdapter
import com.akhil.wavesoffoods.adapter.popularAdapter
import com.akhil.wavesoffoods.databinding.FragmentHomeBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.reflect.KProperty


class HomeFragment : Fragment()
{
    private var binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
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
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewMenu.setOnClickListener {
            val bottomSheetDialog=MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        retrieveAndDisplayPopularItem()
        return binding.root

    }

    private fun retrieveAndDisplayPopularItem()
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
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError)
            {
                TODO("Not yet implemented")
            }

        })

    }

    private fun randomPopularItems()
    {
        val index=menuItems.indices.toList().shuffled()
        val numItemToShow=8
        val subsetMenuItems=index.take(numItemToShow).map { menuItems[it] }
        setPopularItemsAdapter(subsetMenuItems)
    }

    private fun setPopularItemsAdapter(subsetMenuItems: List<MenuItem>)
    {
        val adapter=MenuAdapter(subsetMenuItems, requireContext())
        binding.popularRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter=adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val imageList=ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }
}

private operator fun Any.setValue(
    homeFragment: HomeFragment,
    property: KProperty<*>,
    fragmentHomeBinding: FragmentHomeBinding,
)
{

}




