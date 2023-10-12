package com.akhil.wavesoffoods
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.akhil.wavesoffoods.Fragment.CongratesBottomSheet
import com.akhil.wavesoffoods.Model.OrderDetails
import com.akhil.wavesoffoods.databinding.ActivityPayOutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity()
{
    lateinit var binding: ActivityPayOutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredient: ArrayList<String>
    private lateinit var foodItemQuantities: ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize firebase and user details
        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference()


        //set user data
        setUserData()

        // get user details from firebase
        val intent=intent
        foodItemName=intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice=intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage=intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription=intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredient=intent.getStringArrayListExtra("FoodItemIngredient") as ArrayList<String>
        foodItemQuantities=intent.getIntegerArrayListExtra("FoodItemQuantities") as ArrayList<Int>

        totalAmount=calculateTotalAmount().toString() + " ₹"
        binding.totalAmount.setText(totalAmount)

        binding.buttonBackEdit.setOnClickListener {
            finish()
        }
        binding.placeMyOrderButton.setOnClickListener {
            // get data from textView
            name=binding.name.text.toString().trim()
            address=binding.address.text.toString().trim()
            phone=binding.phoneNo.text.toString().trim()
            if (name.isBlank() && address.isBlank() && phone.isBlank())
            {
                Toast.makeText(this, "Fill All Details", Toast.LENGTH_SHORT).show()
            } else
            {
                placeOrder()
            }

        }
    }

    private fun placeOrder()
    {
        userId=auth.currentUser?.uid ?: ""
        val time=System.currentTimeMillis()
        val itemPushKey=databaseReference.child("OrderDetails").push().key
        val orderDetails=OrderDetails(
            userId,
            name,
            foodItemName,
            foodItemPrice,
            foodItemImage,
            foodItemQuantities,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderReference=databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog=CongratesBottomSheet()

            bottomSheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed To Order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails)
    {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart()
    {
        val cartItemsReference=databaseReference.child("user").child(userId).child("cartItems")
        cartItemsReference.removeValue()
    }

    private fun calculateTotalAmount(): Int
    {
        var totalAamount=0
        for (i in 0 until foodItemPrice.size)
        {
            val price=foodItemPrice[i]
            val lastChar=price.last()
            val priceIntValue=if (lastChar == '₹')
            {
                price.dropLast(1).toInt()
            } else
            {
                price.toInt()
            }
            val quantity=foodItemQuantities[i]
            totalAamount+=priceIntValue * quantity

        }
        return totalAamount
    }

    private fun setUserData()
    {
        val user=auth.currentUser
        if (user != null)
        {
            val userId=user.uid
            val userReference=databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    if (snapshot.exists())
                    {
                        val names=snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses=snapshot.child("address").getValue(String::class.java) ?: ""
                        val phones=snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            name.setText(names)
                            address.setText(addresses)
                            phoneNo.setText(phones)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError)
                {

                }
            })
        }
    }
}