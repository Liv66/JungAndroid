package com.example.jungandroid.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.R
import com.example.jungandroid.model.Photo

class PhotoGridRecyclerViewAdapter : RecyclerView.Adapter<PhotoItemViewHolder>() {

    private var photoList = ArrayList<Photo>()

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {

        return PhotoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        )
    }

    // 뷰가 데이터를 뷰홀더에 넘겨준다
    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bindWithView(this.photoList[position])
    }

    //보여줄 목록 갯수
    override fun getItemCount(): Int {
        return this.photoList.size
    }

    //외부에서 어답터에 데이터 배열을 넘겨준다. 생성자로 대신할 수 있음
    fun submitList(photoList: ArrayList<Photo>){
        this.photoList = photoList
    }
}