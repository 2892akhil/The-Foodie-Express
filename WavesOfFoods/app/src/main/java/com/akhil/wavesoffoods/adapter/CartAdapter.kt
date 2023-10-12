package com.akhil.wavesoffoods.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhil.wavesoffoods.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>, private val cartItemsPrice: MutableList<String>, private val cartImage:MutableList<Int>): RecyclerView.Adapter<CartAdapter.CartViewHolder>()
{
    private val itemQuantities=IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder
    {
        val binding=CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int)
    {
       holder.bind(position)
    }
    override fun getItemCount(): Int=cartItems.size
     inner class CartViewHolder(private val binding: CartItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(position: Int)
        {
          binding.apply {
              val Quantity=itemQuantities[position]
              CartFoodName.text=cartItems[position]
              CartFoodPrice.text=cartItemsPrice[position]
            CartImage.setImageResource(cartImage[position])
              cartItemQuantities.text=Quantity.toString()
              minusButton.setOnClickListener {
               decreaseQuantity(position)
              }
              plusButton.setOnClickListener {
                  increseQuantity(position)
              }
              deleteButton.setOnClickListener {
                  val itemPosition=adapterPosition
                  if (itemPosition!=RecyclerView.NO_POSITION){
                      deleteItem(itemPosition)
                  }
               decreaseQuantity(position)
              }
              }
          }
        private fun increseQuantity(position: Int){
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.cartItemQuantities.text=itemQuantities[position].toString()
            }
        }
        private  fun decreaseQuantity(position: Int)
        {
            if (itemQuantities[position] > 1)
            {
                itemQuantities[position]--
                binding.cartItemQuantities.text=itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
            cartItems.removeAt(position)
            cartItemsPrice.removeAt(position)
            cartImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItems.size)


        }
        }

    }

