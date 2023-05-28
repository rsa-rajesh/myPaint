package com.bts.mypaint.data.repository

import androidcommon.handler.doTryCatch
import androidcommon.handler.handleResponse
import androidcommon.utils.UiState
import com.bts.mypaint.data.apis.AuthApi
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface AuthRepository {
//    suspend fun getServerDetailsFromServer(
//        appID: String,
//        compCode: Int
//    ): UiState<ServerDetailsResponse>?

//    suspend fun getLocalLevelsFromApi(): UiState<ApiLocalLevelResponse>?
//    suspend fun saveLocallevel(data: ApiLocalLevelResponse?): CommonResponse?
//    suspend fun getLocallevelsFromServer(): LocalLevelResponse?
    suspend fun getColorantsFromServer(): UiState<List<TblColorants>>?
    suspend fun getColorsFromServer(): UiState<List<TblColors>>?

}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val connection: ConnectionToServer,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

//    override suspend fun getServerDetailsFromServer(
//        appID: String,
//        compCode: Int
//    ): UiState<ServerDetailsResponse>? {
//        return withContext(dispatcher) {
//            doTryCatch {
//                val basic = Credentials.basic("bts", "Heart@Sun2022")
//                authApi.getServerDetails(basic, appID = appID, compCode).handleResponse()
//            }
//        }
//    }

//    override suspend fun getLocalLevelsFromApi(): UiState<ApiLocalLevelResponse>? {
//        return withContext(dispatcher) {
//            doTryCatch { authApi.getLocalLevelFromApi().handleResponse() }
//        }
//    }

//    override suspend fun saveLocallevel(data: ApiLocalLevelResponse?): CommonResponse? {
//        return connection.saveLocalLevel(data)
//    }
//
//    override suspend fun getLocallevelsFromServer(): LocalLevelResponse? {
//        return connection.getLocalLevel()
//    }

    override suspend fun getColorantsFromServer(): UiState<List<TblColorants>>? {
        return withContext(dispatcher) {
            doTryCatch {
                authApi.getColorants().handleResponse()
            }
        }
    }

    override suspend fun getColorsFromServer(): UiState<List<TblColors>>? {
        return withContext(dispatcher) {
            doTryCatch {
                authApi.getColors().handleResponse()
            }
        }
    }


}