package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Adapter.PesanRecyclerAdapter
import anwarabdullahn.com.villacorp_apps.Model.Pesans
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest
import anwarabdullahn.com.villacorp_apps.Request.PesanRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_pesan.*
import org.jetbrains.anko.toast

class  PesanFragment: Fragment(){

    internal var isLoading: Boolean = false
    internal var pastVisibleItems : Int = 0
    internal var visibleItemCount : Int = 0
    internal var totalItemCount : Int = 0
    internal var previousTotal : Int = 0
    internal var viewThreshold: Int = 12
    val body = PesanRequest()
    var int: Int? = 1
    internal lateinit var adapter : PesanRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contenView = LayoutInflater.from(container?.context).inflate(R.layout.fragment_pesan, container,false)
        val recyclerView = contenView.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(contenView.context, LinearLayout.VERTICAL,false)
        body.page = int

        API.service().pesan(body).enqueue(object : APICallback<Pesans>() {
            override fun onSuccess(pesans: Pesans) {
                activity!!.toast("Berhasil Logout")
                val adapter = PesanRecyclerAdapter(pesans.pesan)
                recyclerView.adapter = adapter
            }

            override fun onError(error: APIError?) {
                activity!!.toast(error!!.msg)
            }

        })

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
                        body.page = int!!.plus(1)
                        activity!!.toast("Load Kedua")
                        Log.d("body",body.page.toString())
                        loadMore()
                        isLoading = true
                    }

                }


            }
        })

        return contenView
    }

    internal fun loadMore(){

        API.service().pesan(body).enqueue(object : APICallback<Pesans>() {
            override fun onSuccess(pesans: Pesans) {
                if (pesans.success == true){
                    adapter.addmore(pesans.pesan)
                } else {

                }
            }

            override fun onError(error: APIError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}

