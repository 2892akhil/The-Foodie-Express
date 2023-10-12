package com.akhil.wavesoffoods.Model

data class CartItems(
    val key:String?=null,
    var foodName: String?=null,
    var foodPrice: String?=null,
    var foodDescription: String?=null,
    var foodImage: String?=null,
    var foodQuantity: Int?=null,
    var foodIngredient: String?=null,
)
