package com.example.adidata.ui.activity.submit_loan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProviders
import com.example.adidata.R
import com.example.adidata.core.BaseActivity
import com.example.adidata.databinding.ActivitySubmitLoanBinding
import com.example.adidata.utils.addTextWatcher

class SubmitLoanActivity : BaseActivity<ActivitySubmitLoanBinding>() {

    private lateinit var viewModel: SubmitLoanViewModel

    override fun getResLayoutId(): Int = R.layout.activity_submit_loan

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(SubmitLoanViewModel::class.java)
        binding.data = viewModel
        binding.btnSubmit.setOnClickListener{
            if (binding.etSubmit.text.toString().toInt() > 12000000)
            {
                showToastInfo("Batas Maksimal Peminjaman 12.000.000!")
            }
            else
            {
                showToastInfo("Data Berhasil Disubmit!")
                finish()
            }


        }



    }
}