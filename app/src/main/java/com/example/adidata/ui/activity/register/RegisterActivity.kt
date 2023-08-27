package com.example.adidata.ui.activity.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.adidata.R
import com.example.adidata.core.BaseActivity
import com.example.adidata.data.User
import com.example.adidata.databinding.ActivityRegisterBinding
import com.example.adidata.utils.DBHelper
import com.example.adidata.utils.NavigationView

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private lateinit var viewModel: RegisterViewModel

    private lateinit var databaseHelper: DBHelper

    override fun getResLayoutId(): Int = R.layout.activity_register

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.data = viewModel

        NavigationView(this).setupNavigationWithAction("Daftar Akun") {
            finish()
        }

        databaseHelper = DBHelper(this)

        binding.btnRegister.setOnClickListener {
            postDataToSQLLite()
        }
    }

    private fun postDataToSQLLite() {
        val email = viewModel.emailField.get().toString().trim()
        val password = viewModel.passwordField.get().toString().trim()
        val confirmPassword = viewModel.confirmPasswordField.get().toString().trim()
        val name = viewModel.nameField.get().toString().trim()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name
                .isEmpty()) {
            showToastDanger("Data tidak boleh kosong")
            return
        }

        if (password != confirmPassword) {
            showToastDanger("Pasword tidak sesuai")
        }

        if (!databaseHelper.checkUser(email, password)) {
            val user = User(name = name, email = email, password = password)

            databaseHelper.registerCustomer(user)
            showToastInfo("Data berhasil disimpan")
            finish()
        } else {
            showToastDanger("Email sudah terdaftar")
        }
    }
}