package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.Category

class HomeActivity : AppCompatActivity() {

    val categoriesList = listOf(
        Category("sports" , R.drawable.sports , "sports" , R.color.color_red),
        Category("technology" , R.drawable.politics , "technology" , R.color.color_blue),
        Category("health" , R.drawable.health , "health" , R.color.color_Pink),
        Category("business" , R.drawable.bussines , "business" , R.color.color_brown),
        Category("general" , R.drawable.enviroment , "general" , R.color.color_sky),
        Category("science" , R.drawable.science , "science" , R.color.color_yellow),
    )
    val adapter = CategoriesAdapter(categoriesList)
    lateinit var recyclerView: RecyclerView
    lateinit var settingsIc: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView = findViewById(R.id.categoriesRV)
        recyclerView.adapter = adapter
        adapter.onItemClickListener = object :CategoriesAdapter.OnItemClickListener
        {
            override fun onItemClick(pos: Int, category: Category) {
                val intent = Intent (applicationContext , CategoriesActivity::class.java)
                intent.putExtra("title" , categoriesList.get(pos).titleId)
                intent.putExtra("id" , categoriesList.get(pos).id)
                startActivity(intent)
            }
        }

        settingsIc = findViewById(R.id.settingsImageHome)
        settingsIc.setOnClickListener {
            intent = Intent(this , SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}