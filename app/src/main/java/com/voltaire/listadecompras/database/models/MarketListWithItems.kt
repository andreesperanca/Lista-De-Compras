package com.voltaire.listadecompras.database.models


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class MarketListWithItems (

    @Embedded val marketList : MarketList,
    @Relation(
        parentColumn = "idList",
        entityColumn = "listIdCreator"
    )
    var itemsLists : @RawValue List<Item>

) : Parcelable