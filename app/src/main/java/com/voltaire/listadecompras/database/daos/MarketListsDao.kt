package com.voltaire.listadecompras.database.daos


import androidx.room.*
import com.voltaire.listadecompras.database.models.Item
import com.voltaire.listadecompras.database.models.MarketList
import com.voltaire.listadecompras.database.models.MarketListWithItems
import kotlinx.coroutines.flow.Flow


@Dao
interface MarketListsDao {

    @Query("SELECT * FROM marketLists ORDER BY name ASC")
    fun getAllLists(): Flow<List<MarketList>>

    @Transaction
    @Query("SELECT * FROM marketLists")
    fun getListsWithItems (): Flow<List<MarketListWithItems>>

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (list : MarketList)

    @Delete
    suspend fun deleteList (list : MarketList)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem (item : Item)

    @Delete
    suspend fun deleteItem (item : Item)

    @Delete
    suspend fun deleteItemList (listItems : List<Item>)

}