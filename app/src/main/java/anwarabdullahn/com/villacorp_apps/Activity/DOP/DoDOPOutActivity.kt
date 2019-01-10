package anwarabdullahn.com.villacorp_apps.Activity.DOP

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.Model.DOPMasuk
import anwarabdullahn.com.villacorp_apps.Model.MaxDOP
import anwarabdullahn.com.villacorp_apps.Model.OffDay
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.r0adkll.slidr.Slidr
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_do_dopout.*
import okhttp3.MultipartBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DoDOPOutActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    lateinit var tglDOP: ArrayList<String>
    lateinit var holidays: ArrayList<String>
    private var new_date: String? = null
    private var max_date: String? = null
    lateinit var body: MultipartBody
    lateinit var postDOPLibur: MultipartBody
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val loadingScreen: DialogFragment = LoadingHelper.getInstance()
    private var dateCalled: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_dopout)

        toolbar.title = "DOP Libur"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        tanggalMasukBtn.setOnClickListener {
            if (dateCalled == 1){
                showDatePicker()
            } else {
                alert("Apakah Anda Yakin Ingin Mengganti Tanggal DOP Masuk", "DOP Libur") {
                    yesButton {
                        finish()
                        startActivity(intent)
                    }
                    noButton {  }
                }.show()
            }

        }

        tanggalLiburBtn.setOnClickListener {
            if (max_date == null){
                toast("Tanggal DOP Masuk Belum dipilih.")
                return@setOnClickListener
            }
            showDatePickerLibur()
        }

        submitBtn.setOnClickListener {
            if (new_date == "" || new_date == null) {
                hideSoftKeyboard(this@DoDOPOutActivity)
                toast("Tanggal DOP Masuk Belum dipilih.")
                return@setOnClickListener
            } else if (tanggalLiburBtn.text == "" || tanggalLiburBtn.text == null) {
                hideSoftKeyboard(this@DoDOPOutActivity)
                toast("Tanggal DOP Libur Belum dipilih.")
                return@setOnClickListener
            }else if (alasanTxt.text.toString() == "") {
                hideSoftKeyboard(this@DoDOPOutActivity)
                toast("Alasan harus diisi.")
                return@setOnClickListener
            }else {
                postDOPLibur = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("tanggalmasuk", tanggalMasukBtn.text.toString())
                    .addFormDataPart("tanggal", tanggalLiburBtn.text.toString())
                    .addFormDataPart("reason", alasanTxt.text.toString())
                    .build()

                loadingScreen.show(supportFragmentManager, "loading Screen")
                API.service().postdop(postDOPLibur).enqueue(object : APICallback<APIResponse>(){
                    override fun onSuccess(t: APIResponse?) {
                        loadingScreen.dismiss()
                        val intent = Intent(this@DoDOPOutActivity, DashboardActivity::class.java)
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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePicker() {
        API.service().dopmasuk().enqueue(object: APICallback<DOPMasuk>() {
            override fun onSuccess(t: DOPMasuk) {

                var calendar = Calendar.getInstance()
                val dpd = DatePickerDialog.newInstance(
                    this@DoDOPOutActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dpd.vibrate(true)
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                tglDOP = ArrayList(t.TglDOPIn)

                var date: java.util.Date? = null
                for (i in 0 until tglDOP.size) {
                    try {
                        date = sdf.parse(tglDOP[i])
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

    private fun showDatePickerLibur() {
        API.service().offday().enqueue(object: APICallback<OffDay>() {
            override fun onSuccess(t: OffDay) {

                var calendar = Calendar.getInstance()
                var _year = calendar.get(Calendar.YEAR)
                var _month = calendar.get(Calendar.MONTH)
                var _day = calendar.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog.newInstance(
                    this@DoDOPOutActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                dpd.vibrate(true)
                dpd.maxDate = GregorianCalendar(_year, _month,_day+6)
                dpd.minDate = calendar
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")


                holidays = ArrayList(t.offday)

                var date: java.util.Date? = null
                var dateMax: java.util.Date? = null
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
                    dateMax = sdf.parse(max_date)
                    dpd.maxDate = dateToCalendar(dateMax)
                    dpd.disabledDays = display
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
        if (dateCalled == 1){
            new_date = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))

            body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tanggal", new_date)
                .build()

            API.service().maxdop(body).enqueue(object: APICallback<MaxDOP>(){
                override fun onSuccess(t: MaxDOP) {
                    max_date = t.tanggal
                    Log.d("MaxDate", t.tanggal)
                }
                override fun onError(error: APIError?) {
                    toast(error!!.msg)
                }
            })
            tanggalMasukBtn.text = new_date
        }else {
            max_date = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))
            tanggalLiburBtn.text = max_date
        }
        dateCalled++
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
