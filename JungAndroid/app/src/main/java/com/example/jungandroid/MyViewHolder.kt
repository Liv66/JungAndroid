package com.example.jungandroid

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jungandroid.databinding.LayoutRecyclerItemBinding

/**
 * 뷰홀더 : 개별 데이터(객체)를 뷰에 바인딩 시킨다.
 * 각 개별 데이터는 뷰홀더 객체로 정의된다.
 * 각 개별 데이터가 어떻게 연결되는지 구현함
 */
class MyViewHolder(
    itemBinding: LayoutRecyclerItemBinding,
    myRecyclerviewInterface: MyRecyclerviewInterface,
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    val TAG: String = "로그"

    private val usernameTextView = itemBinding.userNameTxt
    private val profileImageView = itemBinding.profileImg
    private var myRecyclerviewInterface: MyRecyclerviewInterface? = null

    init {
        itemView.setOnClickListener(this)
        this.myRecyclerviewInterface = myRecyclerviewInterface
    }

    //데이터와 뷰를 묶는다
    fun bind(myModel: MyModel) {
        usernameTextView.text = myModel.name
        Glide
            .with(App.instance)
            .load(myModel.profileImg)
            .placeholder(R.mipmap.ic_launcher)
            .into(profileImageView)
    }

    override fun onClick(p0: View?) {
        Log.d(TAG, "MyViewHolder- onClick() called")
        this.myRecyclerviewInterface?.onItemClicked(adapterPosition)
    }
}