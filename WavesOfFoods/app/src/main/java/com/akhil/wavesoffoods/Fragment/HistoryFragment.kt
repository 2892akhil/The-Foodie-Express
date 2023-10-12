package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.BuyAgainAdapter
import com.akhil.wavesoffoods.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment()
{
    private lateinit var binding:FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding=FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setupRecyclerView()
        return binding.root
    }
    private fun setupRecyclerView(){
        val buyAgainFoodName=arrayListOf("Food1","Food2","Food3","Food4")
        val buyAgainFoodPrice=arrayListOf("$6","$7","$15","$10")
        val buyAgainFoodImage=arrayListOf<Int>(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4)
        buyAgainAdapter=BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
        binding.buyAgainRecyclerView.adapter=buyAgainAdapter
        binding.buyAgainRecyclerView.layoutManager=LinearLayoutManager(requireContext())
    }
    companion object
    {

    }
}