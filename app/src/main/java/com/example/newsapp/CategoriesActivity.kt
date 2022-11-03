package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.*
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var progressbar: ProgressBar
    lateinit var newsRecyclerView: RecyclerView
    val adapter = NewsAdapter(null)
    lateinit var titleTVCA: TextView
    lateinit var id: String
    lateinit var settingsIc: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        tabLayout = findViewById(R.id.tab_layout)
        progressbar = findViewById(R.id.progressbar)
        newsRecyclerView = findViewById(R.id.recyclerview)
        newsRecyclerView.adapter = adapter
        titleTVCA = findViewById(R.id.titleTVCA)
        title = intent.getStringExtra("title")
        id = intent.getStringExtra("id").toString()
        titleTVCA.setText(title)
        getNewsSources()

        adapter.onItemNewsClickListener = object: NewsAdapter.OnItemNewsClickListener{
            override fun onItemNewsClickListener(pos: Int, item: ArticlesItem) {
                val intent = Intent(applicationContext , DetailsActivity::class.java)
                intent.putExtra("Image" , item.urlToImage)
                intent.putExtra("Author" , item.author)
                intent.putExtra("Title" , item.title)
                intent.putExtra("Date" , item.publishedAt)
                intent.putExtra("Details" , item.description)
                startActivity(intent)
            }
        }
        settingsIc = findViewById(R.id.settingsImageCategoryAc)
        settingsIc.setOnClickListener {
            intent = Intent(this , SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getNewsSources() {
        ApiManager.getApis().getSources(Constants.apiKey , id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("error", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressbar.isVisible = false
                    addSourcesToTabLayout(response.body()?.sources)
                }
            })
    }

    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach { source ->
            val tab = tabLayout.newTab()
            tab.setText(source?.name)
            tab.tag = source
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    val source = sources?.get(tab?.position?: 0)
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)
                }

            }
        )
        tabLayout.getTabAt(0)?.select()
    }

    fun getNewsBySource(source: SourcesItem) {
        progressbar.isVisible = true
        ApiManager.getApis()
            .getNews(Constants.apiKey, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressbar.isVisible = false
                    adapter.changeData(response.body()?.articles)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressbar.isVisible = false
                }
            })
    }
}