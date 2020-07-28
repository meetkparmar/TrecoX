package com.bebetterprogrammer.trecox

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth


        btn_next.setOnClickListener {
            val mobileNumber = tv_country_code.text.toString() + et_mobile_number.text.toString()
            if (mobileNumber.trim()
                    .isNotEmpty() && mobileNumber.length == 13
            ) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    mobileNumber, // Phone number to verify
                    60, // Timeout duration
                    TimeUnit.SECONDS, // Unit of timeout
                    this, // Activity (for callback binding)
                    callbacks
                ) // OnVerificationStateChangedCallbacks



            } else {
                Toast.makeText(applicationContext, mobileNumber, Toast.LENGTH_LONG)
                    .show()
            }
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:$credential")

                verificationInProgress = false
                Toast.makeText(applicationContext, "completed", Toast.LENGTH_LONG).show()

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w(TAG, "onVerificationFailed", e)
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()

                verificationInProgress = false

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }


            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                Toast.makeText(applicationContext, "Message Sent Successfully!", Toast.LENGTH_LONG)
                    .show()
                val code = otp_txt.text.toString()
                verifyPhoneNumberWithCode(storedVerificationId, code)   // after putting otp_verify btn in next page of app
            }
        }
        // [END phone_auth_callbacks]
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
//        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
//        private const val STATE_INITIALIZED = 1
//        private const val STATE_VERIFY_FAILED = 3
//        private const val STATE_VERIFY_SUCCESS = 4
//        private const val STATE_CODE_SENT = 2
//        private const val STATE_SIGNIN_FAILED = 5
//        private const val STATE_SIGNIN_SUCCESS = 6
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    Toast.makeText(applicationContext, "Welcome" + user, Toast.LENGTH_LONG).show()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }


}
