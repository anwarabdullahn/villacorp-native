package anwarabdullahn.com.villacorp_apps.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import anwarabdullahn.com.villacorp_apps.Activity.Fragment.*
import anwarabdullahn.com.villacorp_apps.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Activity.TukarLibur.TukarLiburActivity
import anwarabdullahn.com.villacorp_apps.Model.Pesans
import anwarabdullahn.com.villacorp_apps.Request.PesanRequest
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.model.SlidrPosition
import kotlinx.coroutines.*
import org.jetbrains.anko.toast
import kotlin.concurrent.thread
import android.support.v4.os.HandlerCompat.postDelayed




class DashboardActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    val body = PesanRequest()
    var page: Int? = 1

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                fragmentInfo()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_message -> {
                fragmentNotif()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_panduan -> {
                fragmentPanduan()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                fragmentProfile()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (Build.VERSION.SDK_INT >= 23)
        {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),1)
            }
        }

        fragmentHome()
        displayData()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        displayData()
        super.onResume()
        val bundle = intent!!.getStringExtra("result")
        if (bundle != null && bundle != ""){
            Handler().postDelayed(Runnable {
                SnackbarManager.show(
                    Snackbar.with(applicationContext)
                        .text(bundle)
                        .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                        .actionLabel("OK"),
                    this@DashboardActivity
                )
            }, 1500)
            intent.putExtra("result", "")
        }
    }

    override fun onPause() {
        super.onPause()
    }

    fun fragmentHome(){
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.holderFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun fragmentProfile(){
        val transaction = manager.beginTransaction()
        val fragment = ProfileFragment()
        transaction.replace(R.id.holderFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun fragmentNotif(){
        val transaction = manager.beginTransaction()
        val fragment = PesanFragment()
        transaction.replace(R.id.holderFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun fragmentInfo(){
        val transaction = manager.beginTransaction()
        val fragment = InfoFragment()
        transaction.replace(R.id.holderFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun fragmentPanduan(){
        val transaction = manager.beginTransaction()
        val fragment = PanduanFragment()
        transaction.replace(R.id.holderFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun displayData(){
        body.page = page
        API.service().pesan(body).enqueue(object :APICallback<Pesans>() {
            override fun onSuccess(t: Pesans) {
                if(t.jumlah_pesan == "0" ){
                    badgeTxt.visibility = View.GONE
                } else {
                    badgeTxt.visibility = View.VISIBLE
                    badgeTxt.text = t.jumlah_pesan
                }
            }

            override fun onError(error: APIError?) {
                toast(error!!.msg)
            }

        })
    }
}
