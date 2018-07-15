package com.juny.tinderx

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mCallbackManager: CallbackManager? = null

    private val TAG = "FACELOG"

    private var facebookButton: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

    }


    fun phoneClicked(view: View) {

        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)

    }

    fun fbClicked(view: View) {

        facebookButton = findViewById<View>(R.id.facebookButton) as Button?

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create()

        facebookButton!!.setOnClickListener(View.OnClickListener {
            facebookButton!!.setEnabled(false)

            LoginManager.getInstance().logInWithReadPermissions(this@MainActivity, Arrays.asList("email", "public_profile"))
            LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSucess$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }
            })
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    }


    private fun updateUI() {

        Toast.makeText(this, "You're logged in", Toast.LENGTH_LONG).show()

        val intent = Intent(this, DeckActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth!!.getCurrentUser()

                        val intent = Intent(this, DeckActivity::class.java)
                        startActivity(intent)

                        facebookButton!!.setEnabled(true)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@MainActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        facebookButton!!.setEnabled(true)
                    }

                    // ...
                }
    }

}
