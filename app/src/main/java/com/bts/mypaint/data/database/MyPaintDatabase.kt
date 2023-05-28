package com.bts.mypaint.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bts.mypaint.data.dao.TblColorantsDao
import com.bts.mypaint.data.dao.TblColorsDao
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@Database(
    entities = [TblColorants::class, TblColors::class],
    version = 1
)
abstract class MyPaintDatabase : RoomDatabase() {

    abstract fun tblColorantsDao(): TblColorantsDao
    abstract fun tblColorsDao(): TblColorsDao

    private class KanipaniDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Instance?.let { database ->
                scope.launch {
                    val tblColorantsDao = database.tblColorantsDao()
                    val tblColorsDao = database.tblColorsDao()
                    thread {
                        tblColorantsDao.deleteAll()
                        tblColorsDao.deleteAll()
                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var Instance: MyPaintDatabase? = null

        fun getMyPaintDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyPaintDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyPaintDatabase::class.java,
                    "my_paint_colors_database"
                ).addCallback(KanipaniDatabaseCallback(scope = scope)).allowMainThreadQueries().build()
                Instance = instance
                instance
            }
        }
    }
}