package com.freeanycall.app.api

import android.util.Log
import com.freeanycall.app.utils.ApiConstants
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
//                    Here handle if status code 401
                    return NetworkResult.Success(body)
                }
            } else if (response.code() == ApiConstants.UNAUTHORIZED) {
                return unAuthorized("unAuthorized")
            }
            Timber.e("safeApiCall: %s, %s", response.code(), response.message())
            Log.d("TAG", "safeApiCall: "+response.code() + "   "+response.message())
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e("safeApiCall:Exception %s", e.message)
            Log.d("TAG", "safeApiCall: "+e.message)
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Failure("Api call failed $errorMessage")

    private fun <T> unAuthorized(errorMessage: String): NetworkResult<T> =
        NetworkResult.UnAuthorized("Api unAuthorized $errorMessage")

}