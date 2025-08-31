package com.freeanycall.app.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.freeanycall.app.R
import com.freeanycall.app.api.ApiInterface
import com.freeanycall.app.databinding.ActivityCallBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallBinding
    private lateinit var mContext: Context
    private var phoneNumber:String? =null
    @Inject
    lateinit var apiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        mContext= this
        setContentView(binding.root)

        binding.btnCall.setOnClickListener {
            phoneNumber = binding.edtPhone.text.toString()

            if (phoneNumber.isNullOrEmpty()) {
                Toast.makeText(mContext, "Enter phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = HashMap<String, String>()
            request["phone_number"] = phoneNumber!!

            lifecycleScope.launch {
                try {
                    val response = apiInterface.makeCall(request)

                    if (response.isSuccessful && response.body() != null) {
                        val resp = response.body()!!
                        if (resp.status) {
                            Toast.makeText(mContext, "Calling... SID: ${resp.sid}", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(mContext, "Failed: ${resp.message}", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(mContext, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.d("TAG", "onCreate: "+e)
                    Toast.makeText(mContext, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}