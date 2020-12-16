package com.example.networkcats

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var catsRy: RecyclerView
    private lateinit var dogsAdapter: DogsAdapter
    private lateinit var progressBar: ProgressBar
    private var theCatApiServise = NetworkModule.theDogApiServise
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh)

        catsRy = findViewById(R.id.list)
        catsRy.setHasFixedSize(true)

        dogsAdapter = DogsAdapter()
        catsRy.adapter = dogsAdapter

        progressBar = findViewById(R.id.loading)
    }

    override fun onResume() {
        super.onResume()
        repeat(10) {
            progressBar.isVisible = true
            val catsCall: Call<List<DogResponse>> = theCatApiServise.getDogs()
            catsCall.enqueue(object : Callback<List<DogResponse>> {
                override fun onFailure(call: Call<List<DogResponse>>, t: Throwable) {
                    progressBar.isVisible = false
                    toast("Ошибка загрузки изображений")
                }

                override fun onResponse(
                    call: Call<List<DogResponse>>,
                    response: Response<List<DogResponse>>
                ) {
                    val dogs: List<DogResponse> = response.body() ?: emptyList()
                    progressBar.isVisible = false
                    dogsAdapter.addDogs(dogs)
                }

            })
        }
        swipeRefreshLayout.setOnRefreshListener {
            dogsAdapter.cleanDogs()
            repeat(10) {
                swipeRefreshLayout.isRefreshing = true
                val catsCall: Call<List<DogResponse>> = theCatApiServise.getDogs()
                catsCall.enqueue(object : Callback<List<DogResponse>> {
                    override fun onFailure(call: Call<List<DogResponse>>, t: Throwable) {
                        swipeRefreshLayout.isRefreshing = false
                        toast("Ошибка загрузки изображений")
                    }

                    override fun onResponse(
                        call: Call<List<DogResponse>>,
                        response: Response<List<DogResponse>>
                    ) {
                        val dogs: List<DogResponse> = response.body() ?: emptyList()
                        swipeRefreshLayout.isRefreshing = false
                        dogsAdapter.addDogs(dogs)
                    }
                })
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filter = dogsAdapter.getFilter()
                if (filter != null) {
                    filter.filter(newText)
                }//изменений нет
                else
                    Log.d("Debug filter", "onQueryTextChange: Filter is null")
                dogsAdapter.notifyDataSetChanged()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}