package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_do_tukar_libur.*
import java.util.*
import java.text.SimpleDateFormat


class DoTukarLiburActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur)
        Slidr.attach(this)
        toolbar.title = "Tukar Tanggal Libur"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        doTukarTanggalOldDateTxt.text = intent.extras!!.getString("oldDate")

        doTukarTanggalNewDateTxt.setOnClickListener {

            val calendar = Calendar.getInstance()
            var _year = calendar.get(Calendar.YEAR)
            var _month = calendar.get(Calendar.MONTH)
            var _day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, R.style.AlertDialog,

                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val mDate = convertDate(convertToMillis(dayOfMonth, month, year))

                        doTukarTanggalNewDateTxt.text = mDate

                    }, _year, _month, _day)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis + countDay(1)
            datePickerDialog.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //    Date Monday, 23 January 2018
    fun convertDate(mTime: Long): String {
        val df = SimpleDateFormat("EEEE, W MMMM yyyy")
        return df.format(mTime)
    }

    //    Convert To Millis Date
    fun convertToMillis(day: Int, month: Int, year: Int): Long {
        val calendarStart = Calendar.getInstance()
        calendarStart.set(Calendar.YEAR, year)
        calendarStart.set(Calendar.MONTH, month)
        calendarStart.set(Calendar.DAY_OF_MONTH, day)
        return calendarStart.timeInMillis
    }

    fun countDay(day: Int): Int {
        return day*24*60*60*1000
    }


}
