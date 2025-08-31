package com.freeanycall.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.freeanycall.app.MyApplication
import com.freeanycall.app.api.NetworkResult
import com.freeanycall.app.databinding.ActivitySignupBinding
import com.freeanycall.app.di.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var mContext: Context
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        mContext= this
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            signupUser()
        }

        observeSignup()
    }

    private fun signupUser() {
        val firstName = binding.edtFirstName.text.toString().trim()
        val lastName = binding.edtLastName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val termsAccepted = binding.btnTerms.isChecked

        if (firstName.isEmpty()) {
            binding.edtFirstName.error = "Enter first name"
            return
        }
        if (lastName.isEmpty()) {
            binding.edtLastName.error = "Enter last name"
            return
        }
        if (email.isEmpty()) {
            binding.edtEmail.error = "Enter email"
            return
        }
        if (password.isEmpty()) {
            binding.edtPassword.error = "Enter password"
            return
        }
        if (!termsAccepted) {
            Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show()
            return
        }

        val requestMap = hashMapOf(
            "first_name" to firstName,
            "last_name" to lastName,
            "birthday" to "2020-07-12",
            "gender" to "male",
            "email" to email,
            "phone_number" to phone,
            "password" to password,

        )

        profileViewModel.signUp(requestMap)
    }

    private fun observeSignup() {
        profileViewModel.profileResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // success response
                    Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                    // Navigate to next screen


                    startActivity(Intent(mContext, LoginActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    })
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, result.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Pending -> {
                    // show loader
                    Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show()
                }

                NetworkResult.Complete -> TODO()
                is NetworkResult.UnAuthorized -> TODO()
            }
        }
    }
}
