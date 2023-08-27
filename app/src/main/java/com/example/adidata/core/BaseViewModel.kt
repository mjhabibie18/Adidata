package com.example.adidata.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    var errorState = MutableLiveData<Throwable>()

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun subscribe(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun unsubscribe(){
        disposables.dispose()
    }

    fun clearAll(){
        disposables.clear()
    }

    fun Disposable.toDisposables() {
        disposables.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        unsubscribe()
    }

}