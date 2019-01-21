package anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.Adapter.LeaveFinger.LeaveFingerAdapter
import anwarabdullahn.com.villacorp_apps.Model.LeaveFingers
import anwarabdullahn.com.villacorp_apps.Model.OffDay
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import kotlinx.android.synthetic.main.activity_leave_finger.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class LeaveFingerActivity : AppCompatActivity() {

    internal var isLoading: Boolean = false
    internal var pastVisibleItems : Int = 0
    internal var visibleItemCount : Int = 0
    internal var totalItemCount : Int = 0
    internal var previousTotal : Int = 0
    internal var viewThreshold: Int = 12
    var page: Int = 1
    var totalPage: Int? = 0
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()
    lateinit var adapter: LeaveFingerAdapter
    var saldoCuti: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_finger)

        toolbar.title = "Cuti Dibayar"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        recyclerView = find(R.id.recyclerView)
        progressBar = find(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this@LeaveFingerActivity, LinearLayout.VERTICAL,false)
        recyclerView.setHasFixedSize(true)

        loadingScreen.show(supportFragmentManager,"loading")
        content()
        swipeDown()

        swipeUp.setOnRefreshListener{
            swipeUp.scrollTo(0,0)
            swipeUp.isRefreshing = false
            content()
            reset()
            swipeDown()
        }

        cutiDibayarBtn.setOnClickListener {
            if (saldoCuti==0){
                toast("Tidak Memiliki Saldo Cuti silahkan Hubungi HRD")
                return@setOnClickListener
            }
            AnwAPI.service().offday().enqueue(object: AnwCallback<OffDay>(){
                override fun onSuccess(t: OffDay?) {
                    if (t!!.offday.size != 0){
                        val intent = Intent(this@LeaveFingerActivity, DoLeaveFinger::class.java)
                        intent.putExtra("Type", "5")
                        intent.putExtra("Title", "Cuti Tahunan")
                        intent.putExtra("SaldoCuti", saldoCuti)
                        startActivity(intent)
                    } else {
                        toast("Tidak Memiliki Jadwal Libur silahkan Hubungi HRD")
                        return
                    }
                }

                override fun onError(error: AnwError?) {
                    toast(error!!.msg)
                    return
                }

            })
        }

        cutiKhususBtn.setOnClickListener {

            AnwAPI.service().offday().enqueue(object: AnwCallback<OffDay>(){
                override fun onSuccess(t: OffDay?) {
                    if (t!!.offday.size != 0){
                        val intent = Intent(this@LeaveFingerActivity, DoLeaveFinger::class.java)
                        intent.putExtra("Type", "4")
                        intent.putExtra("Title", "Dispensasi / Cuti Khusus")
                        intent.putExtra("SaldoCuti", 30)
                        startActivity(intent)
                    } else {
                        toast("Tidak Memiliki Jadwal Libur silahkan Hubungi HRD")
                        return
                    }
                }

                override fun onError(error: AnwError?) {
                    toast(error!!.msg)
                    return
                }

            })
        }
    }


    private fun reset(){
        page = 1
        isLoading = false
        pastVisibleItems = 0
        visibleItemCount = 0
        totalItemCount = 0
        previousTotal = 0
        viewThreshold = 12
    }

    fun content(){
        val pageparams: Int = page

        page = when {
            pageparams >= totalPage!! -> 1
            else -> page
        }

        AnwAPI.service().leavefinger(page.toString()).enqueue(object : AnwCallback<LeaveFingers>(){
            override fun onSuccess(t: LeaveFingers) {
                if(t.LeaveFinger.size == 0){
                    frameKosong.visibility = View.VISIBLE
                }
                saldoCuti = t.SaldoCuti
                loadingScreen.dismiss()
                totalPage = t.TotalPage.toInt()
                adapter = LeaveFingerAdapter(t.LeaveFinger)
                recyclerView.adapter = adapter
            }

            override fun onError(error: AnwError) {
                loadingScreen.dismiss()
                toast(error.msg)
            }

        })
    }


    fun swipeDown(){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                visibleItemCount = recyclerView.layoutManager!!.childCount
                totalItemCount = recyclerView.layoutManager!!.itemCount
                pastVisibleItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if(dy>0){
                    if (isLoading){
                        if (totalItemCount>previousTotal){
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!isLoading&&(totalItemCount-visibleItemCount)<=(pastVisibleItems+viewThreshold)){
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun loadMore(){
        page += 1
        progressBar.visibility = View.VISIBLE
        AnwAPI.service().leavefinger(page.toString()).enqueue(object : AnwCallback<LeaveFingers>(){
            override fun onSuccess(t: LeaveFingers) {
                progressBar.visibility = View.GONE
                if (t.LeaveFinger.size >= 0){
                    adapter.addmore(t.LeaveFinger)
                } else {
                    toast("Nothing to Load!")
                }
            }
            override fun onError(error: AnwError?) {
                progressBar.visibility = View.GONE
                toast(error!!.msg)
            }

        })
        page = page
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
