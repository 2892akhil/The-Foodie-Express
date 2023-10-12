package com.akhil.wavesoffoods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.Model.OrderDetails
import com.akhil.wavesoffoods.adapter.RecentBuyAdapter
import com.akhil.wavesoffoods.databinding.ActivityRecentOrderItemsBinding

class RecentOrderItems : AppCompatActivity()
{
    private val binding: ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.backButton.setOnClickListener {
            finish()
        }
        val recentOrderItems=
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetail ->
            if (orderDetail.isNotEmpty())
            {
                val recentOrderItem=orderDetail[0]

                allFoodNames=recentOrderItem.foodNames as ArrayList<String>
                allFoodImages=recentOrderItem.foodImages as ArrayList<String>
                allFoodPrices=recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities=recentOrderItem.foodQuantities as ArrayList<Int>


            }
        }
        setAdapter()
    }

    private fun setAdapter()
    {
        val rv=binding.recyclerViewItems
        rv.layoutManager=LinearLayoutManager(this)
        val adapter=
            RecentBuyAdapter(this, allFoodNames, allFoodImages, allFoodPrices, allFoodQuantities)
        rv.adapter=adapter
    }
}