package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import anwarabdullahn.com.villacorp_apps.R
import kotlinx.android.synthetic.main.activity_do_tukar_libur.*
import java.text.SimpleDateFormat
import java.util.*


class DoTukarLiburActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur)

        toolbar.title = "Tukar Tanggal Libur"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        doTukarTanggalOldDateTxt.text = intent.extras!!.getString("oldDate")

        doTukarTanggalNewDateTxt.setOnClickListener {

            val calendar = Calendar.getInstance()
            val _year = calendar.get(Calendar.YEAR)
            val _month = calendar.get(Calendar.MONTH)
            val _day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, R.style.AlertDialog,

                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                        val date: String = (dayOfMonth.toString()  + "/" + (month+1) + "/" + year.toString() )

                        doTukarTanggalNewDateTxt.text = date
                    }, _year, _month, _day)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis + 1*24*60*60*1000
            datePickerDialog.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
