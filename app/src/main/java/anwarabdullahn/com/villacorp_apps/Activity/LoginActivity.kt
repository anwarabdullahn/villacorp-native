package anwarabdullahn.com.villacorp_apps.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.Model.User
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, DashboardActivity::class.java)
        val loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

        if (AnwAPI.isLoggedIn()) {
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            Log.d("userText", usernameTxt.text.toString())
            if ("".equals(usernameTxt.text.trim().toString())) {
                hideSoftKeyboard(this@LoginActivity)
                toast("Username Harus diisi.")
                return@setOnClickListener
            }

            if ("".equals(passwordTxt.text.toString())) {
                hideSoftKeyboard(this@LoginActivity)
                toast("Password Harus diisi.")
                return@setOnClickListener
            }

            val body = LoginRequest()
            body.identity = usernameTxt.text.toString()
            body.password = passwordTxt.text.toString()
            Log.d("identity", body.identity)
            Log.d("password", body.password)

            loadingScreen.show(supportFragmentManager,"loading Screen")
            AnwAPI.service().login(body).enqueue(object : AnwCallback<User>() {
                override fun onSuccess(user: User) {
                    loadingScreen.dismiss()
                    Log.d("Key User", user.key)
                    AnwAPI.setCurrentUser(user)
                    AnwAPI.setToken(user.key)

                    startActivity(intent)
                    finish()
                }

                override fun onError(error: AnwError) {
                    loadingScreen.dismiss()
                    Toast.makeText(this@LoginActivity, error.msg, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}
