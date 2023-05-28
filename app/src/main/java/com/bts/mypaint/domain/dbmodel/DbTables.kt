package com.bts.mypaint.domain.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblColorants")
data class TblColorants(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "colorant_name")
    val colorant_name: String,
    @ColumnInfo(name = "blue", defaultValue = "0")
    val blue: Int,
    @ColumnInfo(name = "colorant_code")
    val colorant_code: String?,
    @ColumnInfo(name = "green", defaultValue = "0")
    val green: Int,
    @ColumnInfo(name = "red", defaultValue = "0")
    val red: Int,
    @ColumnInfo(name = "unit_price", defaultValue = "0")
    val unit_price: Float,
)

@Entity(tableName = "tblColors")
data class TblColors(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "product_name")
    val product_name: String?,

    @ColumnInfo(name = "card_name")
    val card_name: String?,

    @ColumnInfo(name = "shad_code")
    val shad_code: String?,

    @ColumnInfo(name = "shade_name")
    val shade_name: String?,

    @ColumnInfo(name = "new_base")
    val new_base: String?,

    @ColumnInfo(name = "can")
    val can: String?,

    @ColumnInfo(name = "cnt1")
    val cnt1: String?,

    @ColumnInfo(name = "qnt1")
    val qnt1: Float?,

    @ColumnInfo(name = "cnt2")
    val cnt2: String?,

    @ColumnInfo(name = "qnt2")
    val qnt2: Float?,

    @ColumnInfo(name = "cnt3")
    val cnt3: String?,

    @ColumnInfo(name = "qnt3")
    val qnt3: Float?,

    @ColumnInfo(name = "cnt4")
    val cnt4: String?,

    @ColumnInfo(name = "qnt4")
    val qnt4: Float?,

    @ColumnInfo(name = "cnt5")
    val cnt5: String?,

    @ColumnInfo(name = "qnt5")
    val qnt5: Float?,

    @ColumnInfo(name = "red", defaultValue = "0")
    val red: Int,

    @ColumnInfo(name = "green", defaultValue = "0")
    val green: Int,

    @ColumnInfo(name = "blue", defaultValue = "0")
    val blue: Int,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "sizes", defaultValue = "1 Ltr.,4 Ltr.,10 Ltr.,20 Ltr.")
    val sizes: String?,
)


data class TblCalculation(
    val cnt: String,
    val qnt: Float,
    val tblColorants: TblColorants?,

    )