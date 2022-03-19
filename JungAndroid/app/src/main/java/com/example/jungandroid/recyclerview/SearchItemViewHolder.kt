package com.example.jungandroid.recyclerview

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.R
import com.example.jungandroid.model.SearchData
import com.example.jungandroid.utills.Constants.TAG

class SearchItemViewHolder(itemView: View, searchRecyclerViewInterface: ISearchHistoryRecyclerView)
    :RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

    private var mySearchRecyclerViewInterface: ISearchHistoryRecyclerView

    private val searchTermTextView:TextView = itemView.findViewById<TextView>(R.id.search_term_text)
    private val whenSearchedTextView:TextView = itemView.findViewById(R.id.when_searched_text)
    private val deleteSearchBtn = itemView.findViewById<ImageView>(R.id.delete_search_btn)
    private val constraintSearchItem = itemView.findViewById<ConstraintLayout>(R.id.constraint_search_item)

    init {
        // 리스너 연결
        this.mySearchRecyclerViewInterface = searchRecyclerViewInterface
        deleteSearchBtn.setOnClickListener(this)
        constraintSearchItem.setOnClickListener(this)
    }

    fun bindWithView(data :SearchData){
        searchTermTextView.text = data.term
        whenSearchedTextView.text = data.timestamp
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "SearchHistoryViewHolder - onClick() called")
        when(view){
            deleteSearchBtn -> {
                Log.d(TAG, "검색 삭제 버튼 클릭")
                this.mySearchRecyclerViewInterface.onSearchItemDeleteClicked(adapterPosition)
            }
            constraintSearchItem -> {
                Log.d(TAG, "SearchHistoryViewHolder - 검색 아이템  클릭")
                this.mySearchRecyclerViewInterface.onSearchItemClicked(adapterPosition)
            }
        }
    }
}