package com.example.jungandroid.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.R
import com.example.jungandroid.model.SearchData

class SearchHistoryRecyclerViewAdapter(
    var searchHistoryList: ArrayList<SearchData>,
    searchHistoryRecyclerViewInterface: ISearchHistoryRecyclerView
) :
    RecyclerView.Adapter<SearchItemViewHolder>() {

    private var iSearchHistoryRecyclerView : ISearchHistoryRecyclerView? = null

    init {
        this.iSearchHistoryRecyclerView = searchHistoryRecyclerViewInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_search_item, parent, false),
            this.iSearchHistoryRecyclerView!!
        )
    }

    override fun onBindViewHolder(holderSearch: SearchItemViewHolder, position: Int) {

        val dataItem = this.searchHistoryList[position]

        holderSearch.bindWithView(dataItem)
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }
}