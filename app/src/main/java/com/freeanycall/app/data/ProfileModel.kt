package com.freeanycall.app.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileModel(

@SerializedName("status")
    var status:String,
@SerializedName("message")
    var message: String
) : Parcelable{

}
