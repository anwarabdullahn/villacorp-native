package anwarabdullahn.com.villacorp_apps.Activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import anwarabdullahn.com.villacorp_apps.Activity.Fragment.*
import anwarabdullahn.com.villacorp_apps.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Model.Pesans
import anwarabdullahn.com.villacorp_apps.Request.PesanRequest
import org.jetbrains.anko.toast
import java.util.*


class DashboardActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    val body = PesanRequest()
    var page: Int? = 1
    lateinit var timer: Timer

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

        fragmentHome()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        timer = Timer()
        timer.scheduleAtFixedRate(MyTimerTask(),2000,4000)
    }

    fun fragmentHome(){
        val transaction = manager.beginTransaction()
        val fragment = TabHomeFragment()
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

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                displayData()
            }
        }
    }

    fun displayData(){
        body.page = page
        API.service().pesan(body).enqueue(object :APICallback<Pesans>() {
            override fun onSuccess(t: Pesans?) {
                if(t!!.jumlah_pesan == "0" ){
                    badgeTxt.visibility = View.GONE
                } else {
                    badgeTxt.visibility = View.VISIBLE
                    badgeTxt.text = t!!.jumlah_pesan
                }
            }

            override fun onError(error: APIError?) {
                toast(error!!.msg)
            }

        })
    }





}
