package com.freeanycall.app.di.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.freeanycall.app.MyApplication
import com.freeanycall.app.api.NetworkResult
import com.freeanycall.app.base.BaseViewModel
import com.freeanycall.app.data.ProfileModel
import com.freeanycall.app.data.source.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor( private val profileRepository: ProfileRepository, application: Application):
    AndroidViewModel(application) {

        companion object{
val TAG = ProfileModel::class.java.simpleName
        }
    private val _profileResponse: MutableLiveData<NetworkResult<ProfileModel>> =
        MutableLiveData(NetworkResult.Pending)
    val profileResponse: LiveData<NetworkResult<ProfileModel>> = _profileResponse

    fun signUp(requestMap: HashMap<String, String>) = viewModelScope.launch {
        profileRepository.signUp(requestMap).collect { values ->
            _profileResponse.value = values
        }
    }

    fun login(requestMap: HashMap<String, String>) = viewModelScope.launch {
        profileRepository.login(requestMap).collect { values ->
            _profileResponse.value = values
        }
    }
}