package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.R
import kotlinx.android.synthetic.main.activity_in_out.*

class InOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_out)

        toolbar.title = "Telat &amp; Pulang Cepat"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
