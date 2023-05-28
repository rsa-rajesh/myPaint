package com.bts.mypaint.data.repository

import com.bts.mypaint.data.Prefs
import com.bts.mypaint.utils.connectionUtils.SqlServerFunctions
import java.sql.Connection
import java.sql.Statement
import com.bts.mypaint.domain.apiResponse.*

class ConnectionToServer(prefs: Prefs) {
    var prefs: Prefs = prefs
    fun saveLocalLevel(data: ApiLocalLevelResponse?): CommonResponse {
        try {
            val ss = SqlServerFunctions()
            val stmt: Statement?
            val conn: Connection = ss.ConnectToSQLServer(prefs)
            stmt = conn.createStatement()

            if (data != null) {
                for (province in data.province) {
                    val queryProvince =
                        "INSERT INTO tblProvince (state_code, state_name, state_n_name)" +
                                "VALUES ('${province.statecode}','${province.statename}',N'${province.statenname}')"
                    stmt.execute(queryProvince)
                }

//    to save district list
                for (district in data.district) {
                    val queryDistrict =
                        "INSERT INTO tblDistrict (DistrictName, DisCode, District_n_Name,StateCode)" +
                                "VALUES ('${district.districtName}',${district.districtCode.toInt()},N'${district.districtNName}','${district.statecode}')"
                    stmt.execute(queryDistrict)
                }

//                to save local level
                for (localLevel in data.localLevel) {
                    val queryLocalLevel =
                        "INSERT INTO tblLocalLevel (level_code, level_name, level_n_name,category_type,district_code,ward_count)" +
                                "VALUES ('${localLevel.levelcode}','${localLevel.levelname}',N'${localLevel.levelnname}','${localLevel.categorytype}',${localLevel.disctictcode},'${localLevel.wardcount}')"
                    stmt.execute(queryLocalLevel)
                }
            }
            conn.close()
        } catch (e: Exception) {
            return CommonResponse(
                status = "error", message = e.toString()
            )
        }
        return CommonResponse(status = "success", message = "success")
    }

}