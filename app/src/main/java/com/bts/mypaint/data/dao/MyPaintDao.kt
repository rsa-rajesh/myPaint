package com.bts.mypaint.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TblColorantsDao {
    @Query("SELECT * FROM tblColorants")
    abstract fun getAllData(): MutableList<TblColorants>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(table: TblColorants)

    @Query("DELETE FROM tblColorants")
    abstract fun deleteAll()

    @Query("SELECT * FROM tblColorants where colorant_code IN (:colorant) limit 1")
    abstract fun getColorants(colorant: String): TblColorants?
}


@Dao
abstract class TblColorsDao {
    @Query("SELECT * FROM tblColors")
    abstract fun getAllData(): MutableList<TblColors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(table: TblColors)

    @Query("DELETE FROM tblColors")
    abstract fun deleteAll()

    @Query("SELECT * FROM tblColors GROUP by  card_name")
    abstract fun getFanDecks(): Flow<List<TblColors>>

    @Query("SELECT * FROM tblColors where card_name IN (:cardName) GROUP by  product_name")
    abstract fun getProducts(cardName: String?): Flow<List<TblColors>>

    @Query("SELECT * FROM tblColors where card_name =:fandeck AND product_name =:product")
    abstract fun getColors(fandeck: String?, product: String?): Flow<List<TblColors>>
//@Query("SELECT * FROM tblColors")
//abstract fun getColors(): Flow<List<TblColors>>
}
