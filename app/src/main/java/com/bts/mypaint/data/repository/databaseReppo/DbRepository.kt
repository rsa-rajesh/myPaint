package com.bts.mypaint.data.repository.databaseReppo

import com.bts.mypaint.data.dao.TblColorantsDao
import com.bts.mypaint.data.dao.TblColorsDao
import com.bts.mypaint.data.database.MyPaintDatabase
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlinx.coroutines.flow.Flow

class DbRepository(database: MyPaintDatabase) {
    private var tblColorantsDao: TblColorantsDao = database.tblColorantsDao()
    private var tblColorsDao: TblColorsDao = database.tblColorsDao()

    fun insert(colorant: TblColorants) {
        tblColorantsDao.insert(colorant)
    }

    fun insert(color: TblColors) {
        tblColorsDao.insert(color)
    }

    fun deleteColors() {
        tblColorsDao.deleteAll()
    }

    fun deleteColorants() {
        tblColorantsDao.deleteAll()
    }

    fun getAllFandecks(): Flow<List<TblColors>> {
     return  tblColorsDao.getFanDecks()
    }

    fun getAllProducts(cardName: String?): Flow<List<TblColors>> {
        return  tblColorsDao.getProducts(cardName)
    }

    fun getColors(fandeck: String?, product: String?): Flow<List<TblColors>> {
        return  tblColorsDao.getColors(fandeck.toString().trim(),product.toString().trim())
//        return  tblColorsDao.getColors()
    }

    fun getColorants(colorant: String): TblColorants? {
        return  tblColorantsDao.getColorants(colorant)
    }

}