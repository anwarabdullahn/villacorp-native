package anwarabdullahn.com.villacorp_apps.Activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.Fragment.ProfileFragment
import anwarabdullahn.com.villacorp_apps.Activity.Fragment.TabFragment
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    lateinit var mDraweLayout: DrawerLayout
    lateinit var mNavigationView: NavigationView
    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDraweLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        mNavigationView = findViewById(R.id.nav_view) as NavigationView

        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.containerView, TabFragment()).commit()

        mNavigationView.setNavigationItemSelectedListener {
            menuItem -> mDraweLayout.closeDrawers()
            if (menuItem.itemId == R.id.nav_dashboard){
                mNavigationView.getMenu().getItem(0).setChecked(true)
                val ft = mFragmentManager.beginTransaction()
                ft.replace(R.id.containerView, TabFragment()).commit()
            }
            if (menuItem.itemId == R.id.nav_profile){
                mNavigationView.getMenu().getItem(1).setChecked(true)
                val ft = mFragmentManager.beginTransaction()
                ft.replace(R.id.containerView, ProfileFragment()).commit()
            }
            if (menuItem.itemId == R.id.nav_logout){
                logout()
            }
            false
        }
        mNavigationView.getMenu().getItem(0).setChecked(true)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "VillaCorp.Systems"
        val mDrawerToggle = ActionBarDrawerToggle(this, mDraweLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        mDraweLayout.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }

    private fun logout(){
        var loadingScreen: DialogFragment = LoadingHelper.getInstance()
        alert("Apakah Anda Yakin Ingin Keluar", "Logout") {
            yesButton {
                loadingScreen.show(supportFragmentManager,"loading Screen")
                API.service().logout().enqueue(object : APICallback<APIResponse>() {
                    override fun onSuccess(msg: APIResponse?) {
                        loadingScreen.dismiss()
                        startActivity<LoginActivity>()
                        toast("Berhasil Logout")
                        finish()
                        API.logOut()
                    }

                    override fun onError(error: APIError?) {
                        toast(error?.msg.toString())
                    }

                })
            }
            noButton {  }
        }.show()
    }
}
