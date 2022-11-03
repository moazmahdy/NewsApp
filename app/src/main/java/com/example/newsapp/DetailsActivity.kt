package com.example.newsapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.net.URL


class DetailsActivity : AppCompatActivity() {

    lateinit var image:ImageView
    lateinit var author:TextView
    lateinit var title:TextView
    lateinit var date:TextView
    lateinit var details:TextView


    lateinit var imageS:String
    lateinit var authorS:String
    lateinit var titleS:String
    lateinit var dateS:String
    lateinit var detailsS:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        image = findViewById(R.id.detailsIV)
        author = findViewById(R.id.details_author)
        title = findViewById(R.id.details_title)
        date = findViewById(R.id.details_date)
        details = findViewById(R.id.detailsTV)

        titleS = intent.getStringExtra("Title").toString()
        authorS = intent.getStringExtra("Author").toString()
        dateS = intent.getStringExtra("Date").toString()
        detailsS = intent.getStringExtra("Details").toString()
        imageS = intent.getStringExtra("Image").toString()



        title.setText(titleS)
        author.setText(authorS)
        date.setText(dateS)
        details.setText(detailsS)
        val url = URL(imageS)
        Glide.with(image).load(url).into(image)





    }
}

