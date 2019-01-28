package anwarabdullahn.com.villacorp_apps.Activity.IKS

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.Adapter.IKS.SecurityAdapter
import anwarabdullahn.com.villacorp_apps.Model.Securitys
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_choose_security.*
import kotlinx.android.synthetic.main.activity_dop.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.toast

class ChooseSecurityActivity : AppCompatActivity() {

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
    lateinit var adapter: SecurityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_security)

        toolbar.title = "Pilih Sekuriti"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        recyclerView = find(R.id.recyclerView)
        progressBar = find(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this@ChooseSecurityActivity, LinearLayout.VERTICAL,false)
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

        AnwAPI.service().security(page.toString()).enqueue(object : AnwCallback<Securitys>(){
            override fun onSuccess(t: Securitys) {
                if(t.Security.size == 0){
                    frameKosong.visibility = View.VISIBLE
                }
                loadingScreen.dismiss()
                totalPage = t.TotalPage.toInt()
                adapter = SecurityAdapter(t.Security)
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
        AnwAPI.service().security(page.toString()).enqueue(object : AnwCallback<Securitys>(){
            override fun onSuccess(t: Securitys) {
                progressBar.visibility = View.GONE
                if (t.Security.size >= 0){
                    adapter.addmore(t.Security)
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
