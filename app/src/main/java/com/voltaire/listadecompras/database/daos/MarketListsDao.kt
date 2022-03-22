package com.voltaire.listadecompras.database.daos


import androidx.room.*
import com.voltaire.listadecompras.database.models.MarketList
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketListsDao {

    @Query("SELECT * FROM marketLists ORDER BY listName ASC")
    fun getAllLists(): Flow<List<MarketList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (list : MarketList)
}