package com.voltaire.listadecompras.database.models


import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "marketLists")
@Parcelize
data class MarketList(
    @PrimaryKey(autoGenerate = true)  val idList : Long = 0,
    val name: String
) : Parcelable