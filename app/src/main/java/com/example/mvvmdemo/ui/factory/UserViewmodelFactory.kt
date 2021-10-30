package com.example.mvvmdemo.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemo.ui.viewmodel.UserViewModel
import com.example.mvvmdemo.data.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class UserViewmodelFactory(
    private val application: Application,
    val appRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(application, appRepository) as T
    }
}