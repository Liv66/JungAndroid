package com.example.jungandroid

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val itemImage = itemView.findViewById<ImageView>(R.id.pager_item_image)
    private val itemContent = itemView.findViewById<TextView>(R.id.pager_item_text)
    private val itemBg = itemView.findViewById<LinearLayout>(R.id.pager_item_bg)

    fun bindWithView(pageItem: PageItem) {
        itemImage.setImageResource(pageItem.imgSrc)
        itemContent.text = pageItem.content

        //배경색이 흰색이 아닐 때 글자 색 흰색 적용
        if (pageItem.bgColor != R.color.white) {
            itemContent.setTextColor(Color.WHITE)
        }
        itemBg.setBackgroundColor(pageItem.bgColor)
    }
}