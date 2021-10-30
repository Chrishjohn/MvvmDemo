package com.example.mvvmdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.base.BaseActivity
import com.example.mvvmdemo.R
import com.example.mvvmdemo.data.api.Resource
import com.example.mvvmdemo.data.model.UserResponse
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.ui.adapter.UserAdapter
import com.example.mvvmdemo.ui.factory.UserViewmodelFactory
import com.example.mvvmdemo.ui.viewmodel.UserViewModel
import com.example.mvvmdemo.utils.AppUtils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.Executors

class MainActivity : BaseActivity(), KodeinAware {
    private lateinit var binding: ActivityMainBinding
    override val kodein: Kodein by kodein()
    var linerLayoutManager: LinearLayoutManager? = null
    private lateinit var userViewModel: UserViewModel
    private val factory: UserViewmodelFactory by instance()
    var userAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            activity,
            R.layout.activity_main
        )
        setupViewModel()
        setupObservers()
        if (AppUtils.isConnectedToInternet(this@MainActivity)) {

            userViewModel.getSellerlist()
            //       startActivity(Intent(this,))
        }

    }
    //set viewmodel
    private fun setupViewModel() {
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.viewmodel = userViewModel
    }

    //set observer for currency
    private fun setupObservers() {

        userViewModel.userResponse.observe(
            this,
            { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressDialog()
                        response.data?.let { userResponse ->
                     Toast.makeText(activity,"success",Toast.LENGTH_LONG).show()
                           retrieveList(userResponse.data)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressDialog()
                        response.message?.let { message ->
                            showSnackBar(activity, message)
                        }
                    }
                    is Resource.Loading -> {
                        activity?.let { showProgressDialog(it) }
                    }
                }
            })

    }

    private fun retrieveList(data: List<UserResponse.Datum>?) {
        linerLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyList.layoutManager = linerLayoutManager
        userAdapter = data?.let { UserAdapter(it, this) }
        binding.recyList.itemAnimator = DefaultItemAnimator()
        binding.recyList.adapter = userAdapter

    }


}