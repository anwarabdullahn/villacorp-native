package anwarabdullahn.com.villacorp_apps.Activity.DOP

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.Model.DOPHoliday
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.r0adkll.slidr.Slidr
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_do_dopin.*
import okhttp3.MultipartBody
import org.jetbrains.anko.toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DoDOPInActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var holidays: ArrayList<String>
    lateinit var body: MultipartBody
    private var new_date: String? = null
    val loadingScreen: DialogFragment = LoadingHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_dopin)

        toolbar.title = "DOP Masuk"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        tanggalBtn.setOnClickListener {
            showDatePicker()
        }

        submitBtn.setOnClickListener {
            if (alasanTxt.text.toString() == "") {
                hideSoftKeyboard(this@DoDOPInActivity)
                toast("Alasan harus diisi.")
                return@setOnClickListener
            }

            if (new_date == "" || new_date == null) {
                hideSoftKeyboard(this@DoDOPInActivity)
                toast("Tanggal Baru Belum dipilih.")
                return@setOnClickListener
            }
            body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tanggal", tanggalBtn.text.toString())
                .addFormDataPart("reason", alasanTxt.text.toString())
                .build()

            loadingScreen.show(supportFragmentManager, "loading Screen")
            API.service().dopin(body).enqueue(object : APICallback<APIResponse>(){
                override fun onSuccess(t: APIResponse?) {
                    loadingScreen.dismiss()
                    val intent = Intent(this@DoDOPInActivity, DashboardActivity::class.java)
                    intent.putExtra("result", t!!.msg)
                    startActivity(intent)
                }

                override fun onError(error: APIError?) {
                    loadingScreen.dismiss()
                    toast(error!!.msg)
                    return
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }


    fun showDatePicker() {
        API.service().dopholiday().enqueue(object: APICallback<DOPHoliday>() {
            override fun onSuccess(t: DOPHoliday) {

//                Log.d("nganu", t.offday.toString())

                var calendar = Calendar.getInstance()
                val dpd = DatePickerDialog.newInstance(
                    this@DoDOPInActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dpd.vibrate(true)
                dpd.minDate = calendar
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                holidays = ArrayList(t.holiday)

                var date: java.util.Date? = null
                for (i in 0 until holidays.size) {
                    try {
                        date = sdf.parse(holidays[i])
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    calendar = dateToCalendar(date)
                    val dates = ArrayList<Calendar>()
                    dates.add(calendar)
                    val display = dates.toTypedArray()
                    dpd.selectableDays = display
                }

            }

            override fun onError(error: APIError) {
                toast(error.msg)
                return
            }

        })



    }
    private fun dateToCalendar(date: Date?): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        new_date = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))
        tanggalBtn.text = new_date
    }

    //    Date Monday, 23 January 2018
    fun convertDate(mTime: Long): String {
        val df = SimpleDateFormat("EEEE, d MMMM yyyy")
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

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}
