package com.akhil.wavesoffoods.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.Model.OrderDetails
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.RecentOrderItems
import com.akhil.wavesoffoods.adapter.BuyAgainAdapter
import com.akhil.wavesoffoods.databinding.FragmentHistoryBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment()
{
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: ArrayList<OrderDetails> =arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        binding=FragmentHistoryBinding.inflate(layoutInflater, container, false)
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        // retrieve and display the user data history
        retrieveBuyHistory()
        binding.recentBuyItem.setOnClickListener {
            seeItemsRecentbuy()
        }
        binding.receivedButton.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus()
    {
        val itemPushKey=listOfOrderItem[0].itemPushKey
        val completeOrderReference=database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    private fun seeItemsRecentbuy()
    {
        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val intent=Intent(requireContext(), RecentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem)
            startActivity(intent)
        }
    }

    private fun retrieveBuyHistory()
    {
        binding.recentBuyItem.visibility=View.INVISIBLE
        userId=auth.currentUser?.uid ?: ""


        val buyItemReference: DatabaseReference=
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery=buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                for (buySnapShot in snapshot.children)
                {
                    val buyHistoryItem=buySnapShot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty())
                {
                    setDataInRecentBuyItem()
                    setPreviousBuyItemRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError)
            {

            }

        })
    }

    private fun setDataInRecentBuyItem()
    {
        binding.recentBuyItem.visibility=View.VISIBLE
        val recentOrderItem=listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainFoodName.text=it.foodNames?.firstOrNull() ?: ""
                buyAgainFoodPrice.text=it.foodPrices?.firstOrNull() ?: ""
                val image=it.foodImages?.firstOrNull() ?: ""
                val uri=Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)
                val isOrderIsAccepted=listOfOrderItem[0].orderAccepted
                if (isOrderIsAccepted){
                    orderStatus.background.setTint(Color.GREEN)
                    receivedButton.visibility=View.VISIBLE
                }


                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty())
                {

                }

            }
        }
    }

    private fun setPreviousBuyItemRecyclerView()
    {
        val buyAgainFoodName=mutableListOf<String>()
        val buyAgainFoodPrice=mutableListOf<String>()
        val buyAgainFoodImage=mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size)
        {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOrderItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOrderItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }
                val rv=binding.buyAgainRecyclerView
                rv.layoutManager=LinearLayoutManager(requireContext())
                buyAgainAdapter=BuyAgainAdapter(
                    buyAgainFoodName,
                    buyAgainFoodPrice,
                    buyAgainFoodImage,
                    requireContext()
                )
                rv.adapter=buyAgainAdapter
            }
        }
    }
}