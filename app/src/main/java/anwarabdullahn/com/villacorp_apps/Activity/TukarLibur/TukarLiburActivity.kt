package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.content.res.Resources
import android.graphics.drawable.Icon
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.MenuItem

import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.R.drawable.ic_brand
import kotlinx.android.synthetic.main.activity_tukar_libur.*

class TukarLiburActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukar_libur)

        toolbar.title = "Tukar Libur"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
//        tabs.getTabAt(0)!!.icon = getResources().getDrawable(R.drawable.ic_lembur)
//        tabs.getTabAt(0)!!.icon!!.bounds.left
//        tabs.getTabAt(1)!!.icon!!.bounds.left = R.drawable.ic_lembur
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position){
                0 -> {
                    return JadwalFragment()
                }
                1 -> {
                    return ListFragment()
                }
                else -> return null
            }
        }
        override fun getCount(): Int {
            return 2
        }

        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
        }
    }

}
