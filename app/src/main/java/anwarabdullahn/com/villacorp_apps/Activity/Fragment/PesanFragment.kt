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
    var page: Int? = 1
    lateinit var adapter : PesanRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contenView = LayoutInflater.from(container?.context).inflate(R.layout.fragment_pesan, container,false)

        val recyclerView = contenView.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(contenView.context, LinearLayout.VERTICAL,false)
        recyclerView.setHasFixedSize(true)
        body.page = page

        API.service().pesan(body).enqueue(object : APICallback<Pesans>() {
            override fun onSuccess(pesans: Pesans) {
                activity!!.toast("Load Pertama")
                adapter = PesanRecyclerAdapter(pesans.pesan)
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

                        activity!!.toast("Load" + body.page.toString())
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
        body.page = page!! +1
        API.service().pesan(body).enqueue(object : APICallback<Pesans>() {
            override fun onSuccess(pesans: Pesans) {
                if (pesans.success == false){
                    adapter.addmore(pesans.pesan)
                } else {
                    activity!!.toast(pesans.pesan.toString())
                }
            }

            override fun onError(error: APIError?) {
                Log.d("Error" , error!!.msg)
                activity!!.toast(error!!.msg)
            }

        })
        page = body.page.toInt()
    }
}

