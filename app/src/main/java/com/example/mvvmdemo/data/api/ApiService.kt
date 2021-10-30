package com.example.mvvmdemo.data.api

import com.example.mvvmdemo.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {


    @FormUrlEncoded
    @POST(RestConstant.SellerList)
    suspend fun getSellerlist(@FieldMap params: HashMap<String, String?>): Response<UserResponse>

}