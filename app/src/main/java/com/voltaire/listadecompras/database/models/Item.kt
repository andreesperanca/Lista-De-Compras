package com.voltaire.listadecompras.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "items")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true) val idItem: Long = 0,
    val listIdCreator: Long,
    val itemName: String,
    var price: String = "0",
    var amount: String = "0",
    var priceTotalItem : Double = (price.toDouble() * amount.toDouble())

) : Parcelable