package anwarabdullahn.com.villacorp_apps.Activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.Activity.Fragment.*
import anwarabdullahn.com.villacorp_apps.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    val manager = supportFragmentManager

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
}
