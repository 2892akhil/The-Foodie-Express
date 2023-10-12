package com.akhil.wavesoffoods.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhil.wavesoffoods.databinding.RecentBuyItemBinding
import com.bumptech.glide.Glide

class RecentBuyAdapter(
    private var context: Context,
    private var foodNameList: ArrayList<String>,
    private var foodImageList: ArrayList<String>,
    private var foodPriceList: ArrayList<String>,
    private var foodQuantityList: ArrayList<Int>,
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>()
{
    inner class RecentViewHolder(private val binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        fun bind(position: Int)
        {
            binding.apply {
                foodName.text=foodNameList[position]
                val uriString=foodImageList[position]
                val uri=Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImage)
                foodPrice.text=foodPriceList[position]
                foodQuantity.text=foodQuantityList[position].toString()


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder
    {
        val binding=RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int=foodNameList.size


    override fun onBindViewHolder(holder: RecentViewHolder, position: Int)
    {
        holder.bind(position)
    }

}