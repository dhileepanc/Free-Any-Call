package com.freeanycall.app.api

import com.freeanycall.app.data.CallResponse
import com.freeanycall.app.data.ProfileModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("register/")
    suspend fun signup(@Body request: HashMap<String, String>): Response<ProfileModel>

    @POST("login")
    suspend fun login(@Body request: HashMap<String, String>): Response<ProfileModel>

    @POST("call")
    suspend fun makeCall(@Body request: HashMap<String, String>): Response<CallResponse>
}

