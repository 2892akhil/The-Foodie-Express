package com.akhil.wavesoffoods.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhil.wavesoffoods.DetailsActivity
import com.akhil.wavesoffoods.Model.MenuItem
import com.akhil.wavesoffoods.databinding.MenuItemBinding
import com.bumptech.glide.Glide

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context,
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>()
{
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder
    {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int)
    {
        holder.bind(position)
    }

    override fun getItemCount(): Int=menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        init
        {
            binding.root.setOnClickListener {
                val position=adapterPosition
                if (position != RecyclerView.NO_POSITION)
                {
                    openDetailsActivity(position)
                }
            }
        }

        fun bind(position: Int)
        {
            val menuItem: MenuItem=menuItems[position]
            binding.apply {
                MenuFoodName.text=menuItem.foodName
                MenuPrice.text=menuItem.foodPrice
                val uri=Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(MenuImage)

            }
        }

    }

    private fun openDetailsActivity(position: Int)
    {
        // set data in recycler view

        val menuItem: MenuItem=menuItems[position]
        //  a intent to open details activity and pass data
        val intent=Intent(requireContext, DetailsActivity::class.java).apply {
            putExtra("MenuItemName", menuItem.foodName)
            putExtra("MenuItemImage", menuItem.foodImage)
            putExtra("MenuItemPrice", menuItem.foodPrice)
            putExtra("MenuItemDescription", menuItem.foodDescription)
            putExtra("MenuItemIngredient", menuItem.foodIngredient)
        }
        // start the activity details
        requireContext.startActivity(intent)
    }

}


