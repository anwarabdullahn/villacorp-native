package anwarabdullahn.com.villacorp_apps.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.R
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameTxt = findViewById(R.id.username) as EditText
        val passwordTxt = findViewById(R.id.password) as EditText
        val loginBtn    = findViewById(R.id.loginBtn) as Button
        val intent = Intent(this, DashboardActivity::class.java)

        loginBtn.setOnClickListener {
            Log.d("userText", usernameTxt.text.toString())
            if ("".equals(usernameTxt.text.trim().toString())){
                toast("Username Harus diisi.")
                return@setOnClickListener
            }

            if ("".equals(passwordTxt.text.toString())){
                toast("Password Harus diisi.")
                return@setOnClickListener
            }

//            var body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),usernameTxt.text)

//            Log.d("userText", usernameTxt.text.toString())
//
//            API.service().login()


        }

    }
}
