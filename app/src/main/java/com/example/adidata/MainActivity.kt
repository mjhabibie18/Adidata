package com.example.adidata

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.adidata.core.BaseActivity
import com.example.adidata.databinding.ActivityMainBinding
import com.example.adidata.ui.activity.submit_loan.SubmitLoanActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var viewModel: MainViewModel

    override fun getResLayoutId(): Int = R.layout.activity_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.data = viewModel

        binding.btnSubmit.setOnClickListener{
            val intent = Intent(this, SubmitLoanActivity::class.java)
            startActivity(intent)
        }

    }
}