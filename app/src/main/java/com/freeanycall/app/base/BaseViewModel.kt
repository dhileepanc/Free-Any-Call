package com.freeanycall.app.base

import androidx.lifecycle.AndroidViewModel
import com.freeanycall.app.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject


open class BaseViewModel (val application: MyApplication) :
    AndroidViewModel(application) {

    val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    protected val context get() = getApplication<MyApplication>()

}