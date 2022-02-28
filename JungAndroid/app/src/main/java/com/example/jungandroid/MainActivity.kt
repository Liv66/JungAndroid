package com.example.jungandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jungandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyRecyclerviewInterface {

    val TAG: String = "로그"

    lateinit var binding: ActivityMainBinding
    var modelList = ArrayList<MyModel>()
    private lateinit var myRecyclerAdapter: MyRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 1..10) {
            var myModel = MyModel("쩡대리 $i",
                "https://github.githubassets.com/images/modules/open_graph/github-logo.png")
            this.modelList.add(myModel)
        }

        //어댑터 인스턴스 생성
        myRecyclerAdapter = MyRecyclerAdapter(this)
        myRecyclerAdapter.submitList(this.modelList)
        binding.myRecyclerView.apply {
            // 리사이클러뷰  설정
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

            // 어댑터 설정
            adapter = myRecyclerAdapter
        }

    }

    // MyRecyclerViewInterface를 구현한다.
    override fun onItemClicked(position: Int) {
        Log.d(TAG, "MainActivity- onItemClicked() called / $position")

        val title = this.modelList[position].name ?: ""

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage("$title 와 함께하는 빡코딩!")
            .setPositiveButton("오케이") { di, int ->
                Log.d(TAG, "MainActivity- onItemClicked() called")
            }
            .show()

    }
}