package com.example.mvvmdemo.data.repository

import com.example.mvvmdemo.data.api.RetrofitClient


class AppRepository {

    suspend fun getSellerlstRepository(params: HashMap<String, String?>) =
        RetrofitClient.apiInterface.getSellerlist(params = params)

}
