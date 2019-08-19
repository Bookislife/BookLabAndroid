package com.bookislife.booklab.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bookislife.booklab.R
import com.bookislife.booklab.model.Book

class BookAdapter(var items: List<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvBookName.text = item.bookName
        holder.tvAuthor.text = item.author
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBookName: TextView = itemView.findViewById(R.id.bookName)
        val tvAuthor: TextView = itemView.findViewById(R.id.author)
    }
}