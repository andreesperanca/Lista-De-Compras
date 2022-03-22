package com.voltaire.listadecompras.database



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.voltaire.listadecompras.database.daos.MarketListsDao
import com.voltaire.listadecompras.database.models.MarketList
import kotlinx.coroutines.CoroutineScope



@Database (entities = [MarketList::class], version = 1, exportSchema = false)

 abstract class MarketListRoomDataBase : RoomDatabase() {


    abstract fun marketListDao(): MarketListsDao

    companion object {

        @Volatile
        private var INSTANCE: MarketListRoomDataBase? = null

        fun getDatabase(scope: CoroutineScope, context: Context): MarketListRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarketListRoomDataBase::class.java,
                    "marketList_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}