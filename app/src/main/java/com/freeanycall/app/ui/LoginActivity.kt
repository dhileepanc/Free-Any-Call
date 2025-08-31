package com.freeanycall.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.freeanycall.app.R
import com.freeanycall.app.api.NetworkResult
import com.freeanycall.app.databinding.ActivityLoginBinding
import com.freeanycall.app.di.viewModel.ProfileViewModel
import com.freeanycall.app.utils.ApiConstants
import com.freeanycall.app.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mContext: Context
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
        mContext = this
       setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {

            var request=HashMap<String,String>()
            request.put(Constants.TAG_EMAIL,binding.edtEmail.text.toString())
            request.put(Constants.TAG_PASSWORD,binding.edtPassword.text.toString())
            profileViewModel.login(request)

        }

        observeLogin()
    }

    private fun observeLogin() {
        profileViewModel.profileResponse.observe(this) { result ->


            when (result) {

                is NetworkResult.Success -> {
                    var response = result.body
                    Log.d(TAG, "observeLogin: "+Gson().toJson(response))
                    if(response.status.equals(ApiConstants.TAG_TRUE)){

                        // success response
                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                        // Navigate to next screen
                        startActivity(Intent(mContext, CallActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        })
                    }
                    else{
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }

                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, result.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Pending -> {
                    // show loader
                    Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show()
                }

                NetworkResult.Complete -> {

                }
                is NetworkResult.UnAuthorized -> {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}