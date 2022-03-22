package com.voltaire.listadecompras.database.models

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "marketLists")

data class MarketList (

    @PrimaryKey @ColumnInfo(name = "listName") val name : String
)
