package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.MenuAdapter
import com.akhil.wavesoffoods.databinding.FragmentSearchBinding


class SearchFragment : Fragment()
{
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val orignalMenuFoodName=listOf("Herbal Pancake", "Green Noddle", "Ice Cream", "Soup")
    private val orignalMenuItemPrice=listOf("$5", "$7", "$8", "$9")
    private val orignalMenuItemImage=listOf(
        R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4,
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    private val filterMenuFoodName=mutableListOf<String>()
  private  val filterMenuItemPrice=mutableListOf<String>()
    private val filterMenuImage=mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding=FragmentSearchBinding.inflate(inflater, container, false)

        adapter=MenuAdapter(filterMenuFoodName, filterMenuItemPrice, filterMenuImage,requireContext())
        binding.MenuRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.MenuRecyclerView.adapter=adapter

        //setup for searchview
        setupSearchView()
        //set all menuItems
        showAllMenu()

        return binding.root
    }

    private fun showAllMenu()
    {
        filterMenuFoodName.clear()
        filterMenuItemPrice.clear()
        filterMenuImage.clear()
        filterMenuFoodName.addAll(orignalMenuFoodName)
        filterMenuItemPrice.addAll(orignalMenuItemPrice)
        filterMenuImage.addAll(orignalMenuItemImage)
        adapter.notifyDataSetChanged()
    }


    private fun setupSearchView()
    {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String): Boolean
            {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean
            {
                filterMenuItems(newText)
                return true
            }
        })
    }
                private fun filterMenuItems(query: String)
        {
            filterMenuFoodName.clear()
            filterMenuItemPrice.clear()
            filterMenuImage.clear()
            orignalMenuFoodName.forEachIndexed { index, foodName ->
                if (foodName.contains(query,ignoreCase=true)){
                    filterMenuFoodName.add(foodName)
                    filterMenuItemPrice.add(orignalMenuItemPrice[index])
                    filterMenuImage.add(orignalMenuItemImage[index])
            }
        }
            adapter.notifyDataSetChanged()
    }

    companion object
    {

}

}