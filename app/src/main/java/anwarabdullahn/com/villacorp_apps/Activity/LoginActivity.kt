package anwarabdullahn.com.villacorp_apps.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Model.User
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, DashboardActivity::class.java)
        var loadingScreen: DialogFragment = LoadingHelper.getInstance()

        if (API.isLoggedIn()) {
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            Log.d("userText", usernameTxt.text.toString())
            if ("".equals(usernameTxt.text.trim().toString())) {
                toast("Username Harus diisi.")
                return@setOnClickListener
            }

            if ("".equals(passwordTxt.text.toString())) {
                toast("Password Harus diisi.")
                return@setOnClickListener
            }

            val body = LoginRequest()
            body.identity = usernameTxt.text.toString()
            body.password = passwordTxt.text.toString()
            Log.d("identity", body.identity)
            Log.d("password", body.password)

            loadingScreen.show(supportFragmentManager,"loading Screen")
            API.service().login(body).enqueue(object : APICallback<User>() {
                override fun onSuccess(user: User) {
                    loadingScreen.dismiss()
                    Log.d("Key User", user.key)
                    API.setCurrentUser(user)
                    API.setToken(user.key)

                    startActivity(intent)
                    finish()
                }

                override fun onError(error: APIError) {
                    loadingScreen.dismiss()
                    Toast.makeText(this@LoginActivity, error.msg, Toast.LENGTH_SHORT).show()
                }
            })


        }

    }
}
