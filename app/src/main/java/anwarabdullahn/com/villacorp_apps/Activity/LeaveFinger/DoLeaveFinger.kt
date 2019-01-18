package anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.Model.OffDay
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import com.r0adkll.slidr.Slidr
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_do_leave_finger.*
import okhttp3.MultipartBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DoLeaveFinger : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var dateCalled: Int = 1
    lateinit var holidays: ArrayList<String>
    private var dari_tanggal: String? = null
    private var sampai_tanggal: String? = null
    lateinit var postLeaveFinger: MultipartBody
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var max_day: Int? = null
    lateinit var minChoosedDate: Calendar
    val loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_leave_finger)

        var type: String = intent.extras!!.getString("Type")!!.toString()
        var title: String = intent.extras!!.getString("Title")
        var saldoCuti: Int = intent.extras!!.getInt("SaldoCuti")
        max_day = saldoCuti - 1

        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        tanggalBtn.setOnClickListener {
            showDatePicker()
        }

        tanggalBtn2.setOnClickListener {
            if(dari_tanggal==null){
                toast("Dari Tanggal Belum dipilih")
                return@setOnClickListener
            } else if (max_day == null){
                toast("Saldo Cuti Tidak Ditemukan")
                return@setOnClickListener
            }
            showDatePicker2()
        }
    }


    private fun showDatePicker() {
        AnwAPI.service().offday().enqueue(object: AnwCallback<OffDay>() {
            override fun onSuccess(t: OffDay) {

                var calendar = Calendar.getInstance()
                var _year = calendar.get(Calendar.YEAR)
                var _month = calendar.get(Calendar.MONTH)
                var _day = calendar.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog.newInstance(
                    this@DoLeaveFinger,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dpd.vibrate(true)
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")
                dpd.minDate = calendar
                dpd.maxDate = GregorianCalendar(_year, _month,_day+30)

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                holidays = ArrayList(t.offday)

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
                    dpd.disabledDays = display
                }

            }

            override fun onError(error: AnwError) {
                toast(error.msg)
                return
            }

        })
    }

    private fun showDatePicker2() {
        AnwAPI.service().offday().enqueue(object: AnwCallback<OffDay>() {
            override fun onSuccess(t: OffDay) {

                var calendar = Calendar.getInstance()
                var _year = calendar.get(Calendar.YEAR)
                var _month = calendar.get(Calendar.MONTH)
                var _day = calendar.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog.newInstance(
                    this@DoLeaveFinger,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dpd.vibrate(true)
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")
                dpd.minDate = minChoosedDate
                dpd.maxDate = GregorianCalendar(_year, _month,_day + max_day!!.toInt())

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                holidays = ArrayList(t.offday)

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
                    dpd.disabledDays = display
                }

            }

            override fun onError(error: AnwError) {
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
            dari_tanggal = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))
            minChoosedDate = GregorianCalendar(year, monthOfYear,dayOfMonth)
            tanggalBtn.text = dari_tanggal
        }else {
            sampai_tanggal = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))
            tanggalBtn2.text = sampai_tanggal
        }
        dateCalled++
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
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
