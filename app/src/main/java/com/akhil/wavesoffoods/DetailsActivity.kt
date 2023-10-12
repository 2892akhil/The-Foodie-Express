package com.akhil.wavesoffoods

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akhil.wavesoffoods.Model.CartItems
import com.akhil.wavesoffoods.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.NonCancellable.key

class DetailsActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityDetailsBinding
    private var foodName: String?=null
    private var foodImage: String?=null
    private var foodPrice: String?=null
    private var foodDescription: String?=null
    private var foodIngredient: String?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth=FirebaseAuth.getInstance()

        foodName=intent.getStringExtra("MenuItemName")
        foodPrice=intent.getStringExtra("MenuItemPrice")
        foodDescription=intent.getStringExtra("MenuItemDescription")
        foodIngredient=intent.getStringExtra("MenuItemIngredient")
        foodImage=intent.getStringExtra("MenuItemImage")
        with(binding) {
            detailFoodName.text=foodName
            detailDescription.text=foodDescription
            detailIngredient.text=foodIngredient
            detailPrice.text=foodPrice
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
        }


        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.AdddToCart.setOnClickListener {
            addItemToCart()
        }

    }

    private fun addItemToCart()
    {
        val database=FirebaseDatabase.getInstance().reference
        val userId=auth.currentUser?.uid ?: ""
        // create cart item object
        val cartItem=CartItems(
            key.toString(),
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            1,
            foodIngredient.toString()
        )
        //save data to cart item into firebase database
        database.child("user").child(userId).child("cartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Item Added Into Cart Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Item Add into Cart Failed", Toast.LENGTH_SHORT).show()
            }

    }

}