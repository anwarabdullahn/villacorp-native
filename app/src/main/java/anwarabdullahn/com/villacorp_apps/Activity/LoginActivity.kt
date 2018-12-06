package anwarabdullahn.com.villacorp_apps.Activity

import android.app.Application
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import anwarabdullahn.com.villacorp_apps.R
import com.bumptech.glide.request.RequestOptions
//import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameTxt = findViewById(R.id.username) as EditText
        val passwordTxt = findViewById(R.id.password) as EditText
        val loginBtn    = findViewById(R.id.loginBtn) as Button
        val intent = Intent(this, DashboardActivity::class.java)

        loginBtn.setOnClickListener {
            if ("".equals(usernameTxt.text.trim())){
//                toast("Hi there!")
//                toast(R.string.message)
//                longToast("Wow, such a duration")
            }

        }

    }
}
