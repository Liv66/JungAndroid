package com.example.jungandroid.recyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jungandroid.App
import com.example.jungandroid.R
import com.example.jungandroid.model.Photo
import com.example.jungandroid.utills.Constants.TAG


class PhotoItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val photoImageview = itemView.findViewById<ImageView>(R.id.photo_img)
    private val photoCreatedAtText = itemView.findViewById<TextView>(R.id.created_at_txt)
    private val photolikesCountText = itemView.findViewById<TextView>(R.id.likes_count_text)

    //데이터와 뷰를 묶는다
    fun bindWithView(photoItem: Photo){
        Log.d(TAG, "PhotoItemViewHolder - bindWithView() called")

        photoCreatedAtText.text = photoItem.createdAt
        photolikesCountText.text = photoItem.likesCount.toString()

        //이미지 설정은 glide를 사용한다.
        Glide.with(App.instance)
            .load(photoItem.thumbnail)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(photoImageview)
    }

}