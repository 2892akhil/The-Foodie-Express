package com.akhil.wavesoffoods.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhil.wavesoffoods.R
import com.akhil.wavesoffoods.adapter.NotificationAdapter
import com.akhil.wavesoffoods.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomFragment : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?
    {
        binding=FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)
        val notification=listOf(
            "Your Order has been canceled successfully",
            "Order has been taken by driver",
            "Congrats Your order placed"
        )
        val notificationImages=listOf(R.drawable.sademoji, R.drawable.truck, R.drawable.congrats)
        val adapter=NotificationAdapter(ArrayList(notification), ArrayList(notificationImages))
        binding.notificationRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter=adapter
        return binding.root
    }

    companion object
    {

    }
}