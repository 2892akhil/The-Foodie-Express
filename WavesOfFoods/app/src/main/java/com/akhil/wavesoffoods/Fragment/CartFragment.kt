package com.akhil.wavesoffoods.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.PayOutActivity
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.CartAdapter
import com.akhil.wavesoffoods.databinding.FragmentCartBinding


class CartFragment : Fragment()
{
    private lateinit var binding: FragmentCartBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
     binding=FragmentCartBinding.inflate(inflater,container,false)
        val cartFoodName=listOf("Herbal Pancake","Green Noddle","Ice Cream","Soup")
        val cartItemPrice=listOf("$5","$7","$8","$9")
        val cartItemImage=listOf(
            R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,)
        val adapter=CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartItemImage))
        binding.cartRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter=adapter
        binding.proceedButton.setOnClickListener {
            val intent=Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    companion object
    {
            }
    }