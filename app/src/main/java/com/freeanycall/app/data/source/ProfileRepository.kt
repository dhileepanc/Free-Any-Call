package com.freeanycall.app.data.source

import com.freeanycall.app.api.ApiInterface
import com.freeanycall.app.api.BaseApiResponse
import com.freeanycall.app.api.NetworkResult
import com.freeanycall.app.data.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val apiInterface: ApiInterface) : BaseApiResponse() {

    companion object {
        val TAG = ProfileRepository::class.java.simpleName
    }


    suspend fun signUp(requestMap: HashMap<String, String>): Flow<NetworkResult<ProfileModel>> {
        return flow {
            emit(NetworkResult.Pending)
            emit(safeApiCall { apiInterface.signup(requestMap) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(requestMap: HashMap<String, String>): Flow<NetworkResult<ProfileModel>> {
        return flow {
            emit(NetworkResult.Pending)
            emit(safeApiCall { apiInterface.login(requestMap) })
        }.flowOn(Dispatchers.IO)
    }
}