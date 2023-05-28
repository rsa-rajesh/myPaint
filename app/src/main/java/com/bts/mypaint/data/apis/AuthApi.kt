package com.bts.mypaint.data.apis

import com.bts.mypaint.data.Prefs
import com.bts.mypaint.di.DataSourceProperties.BASE_PATH
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {
    companion object {
        private const val userId = Prefs.UserID
        private const val GetColors = "$BASE_PATH/exec?table=colors"
        private const val GetColorants = "$BASE_PATH/exec?table=colorants"
    }

//    @GET(GetColors)
//    suspend fun getServerDetails(@Header("Authorization") userPass:String,@Query("MobAppID") appID: String,@Query("CompCode") compCode: Int): Response<ServerDetailsResponse>
//    @GET(GetColorants)
//    suspend fun getLocalLevelFromApi(): Response<ApiLocalLevelResponse>

    @GET(GetColorants)
    suspend fun getColorants(): Response<List<TblColorants>>

    @GET(GetColors)
    suspend fun getColors(): Response<List<TblColors>>

}