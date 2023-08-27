package com.example.adidata.ui.activity.login

import androidx.databinding.ObservableField
import com.example.adidata.core.BaseViewModel

class LoginViewModel: BaseViewModel() {

    /* Define the live data here*/
    val emailField = ObservableField<String>()
    val passwordField = ObservableField<String>()


}