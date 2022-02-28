package com.example.jungandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.databinding.LayoutRecyclerItemBinding

/**
 *  객체 리스트를 받아서 뷰 홀더를 생성하고 바인딩시킴
 *  adapter 패턴 : 여러 함수를 묶어줌
 */
class MyRecyclerAdapter(myRecyclerviewInterface: MyRecyclerviewInterface) :
    RecyclerView.Adapter<MyViewHolder>() {

    private var modelList = ArrayList<MyModel>()
    private var myRecyclerviewInterface: MyRecyclerviewInterface? = null

    init {
        this.myRecyclerviewInterface = myRecyclerviewInterface
    }

    //뷰홀더가 생성 되었을 떄
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            LayoutRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding, this.myRecyclerviewInterface!!)
    }

    // 뷰와 뷰홀더가 묶였을 떄
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(this.modelList[position])
//      어뎁터에서 직접 클릭 리스너를 설정하는 것은 비용이 많이 발생한다.
//        holder.itemView.setOnClickListener {
//            Toast.makeText(App.instance,
//                "클릭됨! ${this.modelList[position].name}",
//                Toast.LENGTH_SHORT).show()
//        }
    }

    // 목록의 아이템 수
    override fun getItemCount(): Int {
        return this.modelList.size
    }

    fun submitList(modelList: ArrayList<MyModel>) {
        this.modelList = modelList
    }
}