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
    suspend fun getColorantsFromServer(): UiState<List<TblColorants>>?
    suspend fun getColorsFromServer(): UiState<List<TblColors>>?
}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

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