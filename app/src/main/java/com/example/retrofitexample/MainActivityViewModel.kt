package com.example.retrofitexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startVal: Int): ViewModel() {
    private var mutableLiveData: MutableLiveData<Int> = MutableLiveData()

    val res : LiveData<Int> get() = mutableLiveData

    init {
        mutableLiveData.value = startVal
    }

    fun nextQ(){
        mutableLiveData.value = (mutableLiveData.value)?.plus(1)
    }
    fun previous(){
        mutableLiveData.value = (mutableLiveData.value)?.minus(1)
    }
    fun assign(num : Int){
        mutableLiveData.value = num
    }

}