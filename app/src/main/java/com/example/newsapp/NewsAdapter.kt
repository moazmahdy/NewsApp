package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.ArticlesItem
import com.example.newsapp.model.NewsResponse
import com.makeramen.roundedimageview.RoundedImageView

class NewsAdapter(var items: List<ArticlesItem?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.newsTitle.setText(item?.title)
        holder.newsAuthor.setText(item?.author)
        holder.newsDate.setText(item?.publishedAt)
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener {
            if (item != null) {
                onItemNewsClickListener?.onItemNewsClickListener(position , item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size?: 0
    }

    fun changeData(data:List<ArticlesItem?>?){
        items = data
        notifyDataSetChanged()
    }

    var onItemNewsClickListener: OnItemNewsClickListener? = null
    interface OnItemNewsClickListener{
        fun onItemNewsClickListener (pos: Int , item: ArticlesItem)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val newsTitle:TextView = itemView.findViewById(R.id.item_news_title)
        val newsAuthor:TextView = itemView.findViewById(R.id.item_news_author)
        val newsDate:TextView = itemView.findViewById(R.id.item_news_date)
        val newsImage:RoundedImageView = itemView.findViewById(R.id.item_news_image)
    }
}