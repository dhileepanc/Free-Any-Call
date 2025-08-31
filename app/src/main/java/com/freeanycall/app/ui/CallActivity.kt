package com.freeanycall.app.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.freeanycall.app.R
import com.freeanycall.app.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallBinding
    private lateinit var mContext: Context
    private var phoneNumber:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        mContext= this
        setContentView(binding.root)

        binding.btnCall.setOnClickListener {

            phoneNumber = binding.edtPhone.text.toString()



        }

    }
}