package anwarabdullahn.com.villacorp_apps.Activity.DOP

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
import anwarabdullahn.com.villacorp_apps.Adapter.DOP.DOPAdapter
import anwarabdullahn.com.villacorp_apps.Model.DOP
import anwarabdullahn.com.villacorp_apps.Model.DOPMasuk
import anwarabdullahn.com.villacorp_apps.Model.PublicHoliday
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import kotlinx.android.synthetic.main.activity_dop.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.find

class DOPActivity : AppCompatActivity() {

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
    lateinit var adapter: DOPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dop)

        toolbar.title = "DOP"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = find(R.id.recyclerView)
        progressBar = find(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this@DOPActivity, LinearLayout.VERTICAL,false)
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

        dopMasukBtn.setOnClickListener {
            AnwAPI.service().dopholiday().enqueue(object: AnwCallback<PublicHoliday>(){
                override fun onSuccess(t: PublicHoliday?) {
                    if (t!!.holiday.size != 0){
                        val doDOPIn= Intent(this@DOPActivity, DoDOPInActivity::class.java)
                        startActivity(doDOPIn)
                        return
                    } else {
                        toast("Tidak Memiliki Jadwal DOP")
                        return
                    }
                }

                override fun onError(error: AnwError?) {
                    toast(error!!.msg)
                    return
                }

            })
        }

        dopLiburBtn.setOnClickListener {
            AnwAPI.service().dopmasuk().enqueue(object : AnwCallback<DOPMasuk>(){
                override fun onSuccess(t: DOPMasuk?) {
                    if (t!!.TglDOPIn.size != 0){
                        val doDOPIn= Intent(this@DOPActivity, DoDOPOutActivity::class.java)
                        startActivity(doDOPIn)
                        return
                    } else {
                        toast("Tidak Memiliki Jadwal DOP Masuk")
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }


    internal fun reset(){
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

        AnwAPI.service().dop(page.toString()).enqueue(object : AnwCallback<DOP>(){
            override fun onSuccess(t: DOP) {
                if(t.DOPFinger.size == 0){
                    frameKosong.visibility = View.VISIBLE
                }
                loadingScreen.dismiss()
                totalPage = t.totalpage.toInt()
                adapter = DOPAdapter(t.DOPFinger)
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
        AnwAPI.service().dop(page.toString()).enqueue(object : AnwCallback<DOP>(){
            override fun onSuccess(t: DOP) {
                progressBar.visibility = View.GONE
                if (t.DOPFinger.size >= 0){
                    adapter.addmore(t.DOPFinger)
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
}
